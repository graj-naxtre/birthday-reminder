package com.example.birthdayboom.ui.providers

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavigationProvider = staticCompositionLocalOf<NavHostController>{ error("navigation controller isn't initialized yet !") }