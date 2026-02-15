package com.rony.tondo_smart.data

import kotlinx.serialization.Serializable

@Serializable
data class UiJsonSchema(
    val type: String,
    val elements: List<UiElement>
)

@Serializable
data class UiElement(
    val type: String,
    val scope: String? = null,
    val elements: List<UiElement>? = null
)
