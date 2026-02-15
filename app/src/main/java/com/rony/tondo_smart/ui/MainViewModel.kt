package com.rony.tondo_smart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rony.tondo_smart.data.RemoteSchemaDataSource
import com.rony.tondo_smart.domain.FormField
import com.rony.tondo_smart.domain.SchemaRepository
import com.rony.tondo_smart.domain.UiNode
import com.rony.tondo_smart.domain.validators.validateAndReturn
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: SchemaRepository
): ViewModel() {

    private val _formFields = MutableStateFlow<List<FormField>>(emptyList())
    val formFields = _formFields.asStateFlow()

    private val _uiSchema = MutableStateFlow<UiNode?>(null)
    val uiSchema = _uiSchema.asStateFlow()

    private val _events = MutableSharedFlow<MainEvents>()
    val events = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            val dataDeferred = async { repository.loadFormFields() }
            val uiDeferred = async { repository.loadUiSchema() }
            val fields = dataDeferred.await()
            val ui = uiDeferred.await()
            _formFields.value = fields
            _uiSchema.value = ui
        }
    }

    fun updateText(id: String, value: String) {
        _formFields.update { fields ->
            fields.map {
                if (it is FormField.TextField && it.id == id) {
                    it.copy(value = value, error = null)
                } else it
            }
        }
    }

    fun updateNumber(id: String, value: String) {
        _formFields.update { fields ->
            fields.map {
                if (it is FormField.NumberField && it.id == id) {
                    it.copy(value = value, error = null)
                } else it
            }
        }
    }

    fun updateBoolean(id: String, value: Boolean) {
        _formFields.update { fields ->
            fields.map {
                if (it is FormField.BooleanField && it.id == id) {
                    it.copy(value = value)
                } else it
            }
        }
    }

    fun updateDropdown(id: String, value: String) {
        _formFields.update { fields ->
            fields.map {
                if (it is FormField.DropdownField && it.id == id) {
                    it.copy(selected = value, error = null)
                } else it
            }
        }
    }

    fun onSubmitClicked() {
        val validatedFields = _formFields.value.map { it.validateAndReturn() }

        val isFormValid = validatedFields.all { field ->
            when(field) {
                is FormField.TextField -> field.error == null
                is FormField.NumberField -> field.error == null
                is FormField.DropdownField -> field.error == null
                else -> true
            }
        }

        _formFields.value = validatedFields
        viewModelScope.launch {
            if(isFormValid) {
                _events.emit(MainEvents.SuccessMessage("All fields are valid!"))
            } else {
                _events.emit(MainEvents.ErrorMessage("Fields aren't valid!. Please fix them."))
            }
        }
    }
}