package com.rony.tondo_smart.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rony.tondo_smart.domain.FormField
import com.rony.tondo_smart.domain.UiNode
import com.rony.tondo_smart.ui.MainViewModel

@Composable
fun RenderUiNode(
    node: UiNode,
    fields: List<FormField>,
    viewModel: MainViewModel
) {
    when (node) {

        is UiNode.VerticalLayout -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                node.children.forEach {
                    RenderUiNode(it, fields, viewModel)
                }
            }
        }

        is UiNode.HorizontalLayout -> {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                node.children.forEach { child ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        RenderUiNode(child, fields, viewModel)
                    }
                }
            }
        }

        is UiNode.Control -> {
            val field = fields.find { it.id == node.fieldId }
            field?.let {
                RenderField(it, viewModel)
            }
        }

        else -> Unit
    }
}