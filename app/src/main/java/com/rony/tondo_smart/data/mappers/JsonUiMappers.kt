package com.rony.tondo_smart.data.mappers

import com.rony.tondo_smart.data.UiElement
import com.rony.tondo_smart.data.UiJsonSchema
import com.rony.tondo_smart.domain.UiNode

fun UiElement.toDomain(): UiNode {
    return when (type) {

        "VerticalLayout" -> UiNode.VerticalLayout(
            children = elements?.map { it.toDomain() } ?: emptyList()
        )

        "HorizontalLayout" -> UiNode.HorizontalLayout(
            children = elements?.map { it.toDomain() } ?: emptyList()
        )

        "Control" -> UiNode.Control(
            fieldId = scope!!.substringAfterLast("/")
        )

        else -> UiNode.NotSupportedLayout
    }
}

fun UiJsonSchema.toDomain(): UiNode {
    return UiElement(
        type = type,
        elements = elements
    ).toDomain()
}