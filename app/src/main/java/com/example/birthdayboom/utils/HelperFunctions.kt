package com.example.birthdayboom.utils

fun String.isValidPhoneNumber() : Boolean {
    val regex = "^[0-9]{10}$".toRegex()
    return this.matches(regex)
}