package com.rony.tondo_smart.data

import kotlinx.serialization.Serializable

@Serializable
data class JsonSchema(
    val type: String,
    val properties: Map<String, JsonFieldSchema>,
    val required: List<String> = emptyList()
)

@Serializable
data class JsonFieldSchema(
    val type: String,
    val minLength: Int? = null,
    val maxLength: Int? = null,
    val minimum: Double? = null,
    val maximum: Double? = null,
    val enum: List<String>? = null
)
