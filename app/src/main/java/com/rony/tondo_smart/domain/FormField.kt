package com.rony.tondo_smart.domain

sealed class FormField {

    abstract val id: String
    abstract val isRequired: Boolean
    abstract val error: String?

    data class TextField(
        override val id: String,
        override val isRequired: Boolean,
        val minLength: Int?,
        val maxLength: Int?,
        var value: String = "",
        override var error: String? = null
    ) : FormField()

    data class NumberField(
        override val id: String,
        override val isRequired: Boolean,
        val minimum: Double?,
        val maximum: Double?,
        var value: String = "",
        override var error: String? = null
    ) : FormField()

    data class BooleanField(
        override val id: String,
        override val isRequired: Boolean,
        var value: Boolean = false,
        override var error: String? = null
    ) : FormField()

    data class DropdownField(
        override val id: String,
        override val isRequired: Boolean,
        val options: List<String>,
        var selected: String? = null,
        override var error: String? = null
    ) : FormField()

    data class NotSupportedField(
        override val id: String = "",
        override val isRequired: Boolean = false,
        override var error: String? = null
    ): FormField()
}

fun List<FormField>.toPayload(): Map<String, Any?> {
    return this.associate { field ->
        field.id to when (field) {
            is FormField.TextField -> field.value
            is FormField.NumberField -> field.value.toDoubleOrNull()
            is FormField.BooleanField -> field.value
            is FormField.DropdownField -> field.selected
            is FormField.NotSupportedField -> null
        }
    }
}