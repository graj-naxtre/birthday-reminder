package com.example.birthdayboom.ui.navigations.navgraph

sealed class BirthdayScreens(val route: String, val name: String) {
    data object Birthdays : BirthdayScreens(route = "birthdays", name = "Birthday")

    data object ViewPerson : BirthdayScreens(route = "view_person", name = "View Person") {
        fun getBirthdayRoute(): String {
            return "view_person/{person_id}"
        }

        fun getBirthdayRouteWithArgs(personId: Int): String {
            return "view_person/$personId"
        }
    }
}