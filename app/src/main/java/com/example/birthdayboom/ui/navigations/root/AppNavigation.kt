package com.example.birthdayboom.ui.navigations.root


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.birthdayboom.ui.navigations.birthday_graph.BirthdayGraphRoute
import com.example.birthdayboom.ui.navigations.birthday_graph.birthdayNavGraph
import com.example.birthdayboom.ui.navigations.contact_graph.contactNavGraph
import com.example.birthdayboom.ui.navigations.setting_graph.SettingRoute
import com.example.birthdayboom.ui.screens.setting.SettingScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BirthdayGraphRoute
    ) {
        birthdayNavGraph()

        contactNavGraph()

        composable<SettingRoute> {
            SettingScreen()
        }
    }
}