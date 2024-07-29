package com.example.birthdayboom.ui.navigations


import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.birthdayboom.ui.navigations.navgraph.birthdayNavGraph
import com.example.birthdayboom.ui.navigations.navgraph.contactNavGraph
import com.example.birthdayboom.ui.screens.setting.SettingScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = birthdayNavigation,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        birthdayNavGraph(navController)

        contactNavGraph(navController)

        composable(route = settingNavigation) {
            SettingScreen()
        }
    }
}