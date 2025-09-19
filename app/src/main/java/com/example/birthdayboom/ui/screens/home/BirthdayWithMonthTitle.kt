package com.example.birthdayboom.ui.screens.home

sealed class BirthdayWithMonthTitle {
    data class Title(val text: String) : BirthdayWithMonthTitle()
    data class Item(val data: BirthdayCardInfo) : BirthdayWithMonthTitle()
}