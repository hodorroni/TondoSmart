package com.rony.tondo_smart.ui

sealed interface MainEvents {
    data class SuccessMessage(val message: String): MainEvents
    data class ErrorMessage(val message: String): MainEvents
}