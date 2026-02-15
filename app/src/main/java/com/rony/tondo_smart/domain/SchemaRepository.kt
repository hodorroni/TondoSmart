package com.rony.tondo_smart.domain

interface SchemaRepository {
    suspend fun loadFormFields(): List<FormField>
    suspend fun loadUiSchema(): UiNode
}