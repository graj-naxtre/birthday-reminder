package com.example.birthdayboom.ui.navigations.birthday_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.birthdayboom.ui.screens.home.HomeScreen

fun NavGraphBuilder.birthdayNavGraph() {
    navigation<BirthdayGraphRoute>(startDestination = BirthdayListRoute) {
        composable <BirthdayListRoute>{
            HomeScreen()
        }
    }
}