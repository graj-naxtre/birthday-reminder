package com.example.birthdayboom.ui.navigations

import com.example.birthdayboom.R

const val birthdayNavigation = "birthdayNavigation"
const val contactNavigation = "contactNavigation"
const val settingNavigation = "settingNavigation"

sealed class BottomNavigationDestinations(
    val title: String,
    val iconSelected: Int,
    val iconUnSelected: Int,
    val route: String
) {
    object Birthdays : BottomNavigationDestinations(
        title = "Birthdays",
        iconSelected = R.drawable.filled_cake_24,
        iconUnSelected = R.drawable.outline_cake_24,
        route = birthdayNavigation
    )
    object Contacts : BottomNavigationDestinations(
        title = "Contacts",
        iconSelected = R.drawable.filled_contact_24,
        iconUnSelected = R.drawable.outline_contact_24,
        route = contactNavigation
    )
    object Settings : BottomNavigationDestinations(
        title = "Settings",
        iconSelected = R.drawable.filled_settings_24,
        iconUnSelected = R.drawable.outline_settings_24,
        route = settingNavigation
    )
}