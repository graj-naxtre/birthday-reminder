package com.example.birthdayboom.utility.communication

sealed class Communication {
    data class MakeCall(val number: String) : Communication()
    data class SendMessage(val number: String, val message: String) : Communication()
}