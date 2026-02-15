package com.rony.tondo_smart.data.mappers

import com.rony.tondo_smart.data.JsonSchema
import com.rony.tondo_smart.domain.FormField

fun JsonSchema.toFormFields(): List<FormField> {

    return properties.map { (key, fieldSchema) ->
        val required = required.contains(key)

        when {
            fieldSchema.enum != null -> {
                FormField.DropdownField(
                    id = key,
                    isRequired = required,
                    options = fieldSchema.enum,
                    selected = fieldSchema.enum.firstOrNull()
                )
            }

            fieldSchema.type == "string" -> {
                FormField.TextField(
                    id = key,
                    isRequired = required,
                    minLength = fieldSchema.minLength,
                    maxLength = fieldSchema.maxLength
                )
            }

            fieldSchema.type == "number" -> {
                FormField.NumberField(
                    id = key,
                    isRequired = required,
                    minimum = fieldSchema.minimum,
                    maximum = fieldSchema.maximum
                )
            }

            fieldSchema.type == "boolean" -> {
                FormField.BooleanField(
                    id = key,
                    isRequired = required
                )
            }
            else -> FormField.NotSupportedField()
        }
    }
}