package com.example.birthdayboom.ui.navigations.navgraph

sealed class ContactScreens(val route: String, val name: String) {
    data object Contacts : ContactScreens(route = "contacts", name = "Contact")

    data object ViewPerson :
        ContactScreens(route = "view_person", name = "View Person") {

        fun getContactRoute(): String {
            return "view_person/{person_id}"
        }
        fun getContactRouteWithArgs(personId: Int): String {
            return "view_person/$personId"
        }
    }

    data object AddContact : ContactScreens(route = "add_contact", name = "Add Contact")
}