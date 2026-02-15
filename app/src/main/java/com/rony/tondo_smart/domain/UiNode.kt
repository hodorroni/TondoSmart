package com.rony.tondo_smart.domain

sealed class UiNode {

    data class VerticalLayout(
        val children: List<UiNode>
    ) : UiNode()

    data class HorizontalLayout(
        val children: List<UiNode>
    ) : UiNode()

    data class Control(
        val fieldId: String
    ) : UiNode()

    data object NotSupportedLayout: UiNode()
}