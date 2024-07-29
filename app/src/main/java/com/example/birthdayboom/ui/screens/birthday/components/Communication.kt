package com.example.birthdayboom.ui.screens.birthday.components

sealed class Communication {
    data class MakeCall(val number: String) : Communication()
    data class SendMessage(val number: String, val message: String) : Communication()
}