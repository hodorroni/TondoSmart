package com.rony.tondo_smart.domain.validators

import com.rony.tondo_smart.domain.FormField

fun FormField.validateAndReturn(): FormField {
    return when (this) {

        is FormField.TextField -> {
            when {
                isRequired && value.isBlank() ->
                    copy(error = "Required field")

                minLength != null && value.length < minLength ->
                    copy(error = "Minimum length is $minLength")

                else -> copy(error = null)
            }
        }

        is FormField.NumberField -> {
            val number = value.toDoubleOrNull()

            when {
                isRequired && value.isBlank() ->
                    copy(error = "Required field")

                number != null && minimum != null && number < minimum ->
                    copy(error = "Minimum value is ${minimum.toInt()}")

                number != null && maximum != null && number > maximum ->
                    copy(error = "Maximum value is ${maximum.toInt()}")

                else -> copy(error = null)
            }
        }

        is FormField.DropdownField -> {
            when {
                isRequired && selected == null ->
                    copy(error = "Please select an option.")

                else -> copy(error = null)
            }
        }

        is FormField.BooleanField -> {
            copy(error = null)
        }

        is FormField.NotSupportedField -> this
    }
}
