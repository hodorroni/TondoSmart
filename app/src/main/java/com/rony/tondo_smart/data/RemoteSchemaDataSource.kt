package com.rony.tondo_smart.data

import com.rony.tondo_smart.data.mappers.toDomain
import com.rony.tondo_smart.data.mappers.toFormFields
import com.rony.tondo_smart.domain.FormField
import com.rony.tondo_smart.domain.SchemaRepository
import com.rony.tondo_smart.domain.UiNode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class RemoteSchemaDataSource(
    private val dispatcher: CoroutineDispatcher
): SchemaRepository {

    private val jsonParser = Json { ignoreUnknownKeys = true }

    override suspend fun loadFormFields(): List<FormField> {
        return withContext(dispatcher) {

            val schemaJson = """
            {
              "type": "object",
              "properties": {
                "name": {
                  "type": "string",
                  "minLength": 3
                },
                "age": {
                  "type": "number",
                  "minimum": 18
                },
                "isStudent": {
                  "type": "boolean"
                },
                "country": {
                  "type": "string",
                  "enum": ["Israel", "USA", "Germany"]
                }
              },
              "required": ["name", "age"]
            }
            """.trimIndent()

            val schema = jsonParser.decodeFromString<JsonSchema>(schemaJson)

            schema.toFormFields()
        }
    }

    override suspend fun loadUiSchema(): UiNode {
        return withContext(dispatcher) {

            val uiJson = """
        {
          "type": "VerticalLayout",
          "elements": [
            {
              "type": "HorizontalLayout",
              "elements": [
                { "type": "Control", "scope": "#/properties/name" },
                { "type": "Control", "scope": "#/properties/age" }
              ]
            },
            { "type": "Control", "scope": "#/properties/isStudent" },
            { "type": "Control", "scope": "#/properties/country" }
          ]
        }
        """.trimIndent()
            val dto = jsonParser.decodeFromString<UiJsonSchema>(uiJson)
            dto.toDomain()
        }
    }
}