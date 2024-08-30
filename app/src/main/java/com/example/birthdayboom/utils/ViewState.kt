package com.example.birthdayboom.utils

sealed class ViewState {
    data object Idle : ViewState()
    data object Loading : ViewState()
    data class Error(val message: String = "Unknown Error") : ViewState()
}