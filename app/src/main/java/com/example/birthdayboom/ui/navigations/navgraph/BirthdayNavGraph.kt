package com.example.birthdayboom.ui.navigations.navgraph

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.birthdayboom.ui.navigations.birthdayNavigation
import com.example.birthdayboom.ui.screens.ProfileScreen
import com.example.birthdayboom.ui.screens.birthday.BirthdayScreenV2

fun NavGraphBuilder.birthdayNavGraph(
    navController: NavHostController,
    startDestination: String = BirthdayScreens.Birthdays.route
) {

    navigation(startDestination = startDestination, route = birthdayNavigation) {
        composable(
            route = BirthdayScreens.Birthdays.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            BirthdayScreenV2(
//                handleNavigationAction = { event -> handleBirthdayNavigation(event, navController) }
            )
        }

        composable(
            route = BirthdayScreens.ViewPerson.getBirthdayRoute(),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) { navBackStackEntry ->
            val contactId = navBackStackEntry.arguments?.getString("person_id")
            if (contactId != null) {
                ProfileScreen(contactId.toInt())
            }
        }
    }
}

fun handleBirthdayNavigation(event: BirthdayNavigationEvent, navController: NavHostController) {
    when (event) {
        is BirthdayNavigationEvent.OnProfileClick -> {
            navController.navigate(BirthdayScreens.ViewPerson.getBirthdayRouteWithArgs(event.contactId))
        }
    }
}

sealed class BirthdayNavigationEvent {
    data class OnProfileClick(val contactId: Int) : BirthdayNavigationEvent()
}