package com.example.birthdayboom.ui.navigations.navgraph

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.birthdayboom.ui.navigations.contactNavigation
import com.example.birthdayboom.ui.screens.ProfileScreen
import com.example.birthdayboom.ui.screens.add_birthday.AddBirthdayScreenV2
import com.example.birthdayboom.ui.screens.contact.ContactScreenV2

fun NavGraphBuilder.contactNavGraph(
    navController: NavHostController,
    startDestination: String = ContactScreens.Contacts.route
) {
    navigation(startDestination = startDestination, route = contactNavigation) {
        composable(
            route = ContactScreens.Contacts.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            ContactScreenV2(handleNavigationAction = { event ->
                handleContactNavigation(event, navController)
            })
        }

        composable(
            route = ContactScreens.ViewPerson.getContactRoute(),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) { navBackStackEntry ->
            val personId = navBackStackEntry.arguments?.getString("person_id")
            if (personId != null) {
                ProfileScreen(contactId = personId.toInt())
            }
        }

        composable(
            route = ContactScreens.AddContact.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            AddBirthdayScreenV2(handleNavigationAction = { event ->
                handleContactNavigation(event, navController)
            })
        }
    }
}

fun handleContactNavigation(event: ContactNavigationEvent, navController: NavHostController) {
    when (event) {
        is ContactNavigationEvent.OnProfileClick -> {
            navController.navigate(ContactScreens.ViewPerson.getContactRouteWithArgs(event.contactId))
        }

        ContactNavigationEvent.OnAddContactClick -> {
            navController.navigate(ContactScreens.AddContact.route)
        }

        ContactNavigationEvent.OnSaveContactClick -> {
            navController.popBackStack()
        }
    }
}

sealed class ContactNavigationEvent {
    data class OnProfileClick(val contactId: Int) : ContactNavigationEvent()

    data object OnAddContactClick : ContactNavigationEvent()

    data object OnSaveContactClick : ContactNavigationEvent()
}