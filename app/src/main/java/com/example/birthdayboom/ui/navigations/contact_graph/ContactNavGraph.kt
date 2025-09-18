package com.example.birthdayboom.ui.navigations.contact_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.birthdayboom.ui.screens.add_birthday.AddBirthdayScreen
import com.example.birthdayboom.ui.screens.contact.ContactScreen

fun NavGraphBuilder.contactNavGraph() {
    navigation<ContactGraphRoute>(startDestination = ContactListRoute) {
        composable<ContactListRoute> {
            ContactScreen()
        }

        composable<AddContactRoute> {
            AddBirthdayScreen()
        }
    }
}