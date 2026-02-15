package com.rony.tondo_smart.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rony.tondo_smart.domain.FormField
import com.rony.tondo_smart.ui.MainViewModel

@Composable
fun RenderField(
    field: FormField,
    viewModel: MainViewModel
) {
    when (field) {
        is FormField.TextField -> {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = field.value,
                onValueChange = {
                    viewModel.updateText(field.id, it)
                },
                label = {
                    Text(
                        text = buildLabel(field.id, field.isRequired)
                    )
                },
                isError = field.error != null,
                supportingText = {
                    field.error?.let { Text(it) }
                }
            )
        }

        is FormField.NumberField -> {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = field.value,
                onValueChange = {
                    viewModel.updateNumber(field.id, it)
                },
                label = {
                    Text(buildLabel(field.id, field.isRequired))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                isError = field.error != null,
                supportingText = {
                    field.error?.let { Text(it) }
                }
            )
        }

        is FormField.BooleanField -> {
            CompositionLocalProvider(
                LocalMinimumInteractiveComponentSize provides 0.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Checkbox(
                        checked = field.value,
                        onCheckedChange = {
                            viewModel.updateBoolean(field.id, it)
                        }
                    )
                    Text(buildLabel(field.id, field.isRequired))
                }
            }
        }

        is FormField.DropdownField -> {
            DropdownFieldComposable(field, viewModel)
        }

        is FormField.NotSupportedField -> Unit
    }
}

fun buildLabel(id: String, required: Boolean): String {
    return if (required) "$id *" else id
}