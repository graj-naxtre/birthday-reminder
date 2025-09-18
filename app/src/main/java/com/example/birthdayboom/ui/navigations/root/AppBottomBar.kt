package com.example.birthdayboom.ui.navigations.root

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.birthdayboom.R
import com.example.birthdayboom.ui.navigations.birthday_graph.BirthdayGraphRoute
import com.example.birthdayboom.ui.navigations.contact_graph.ContactGraphRoute
import com.example.birthdayboom.ui.navigations.setting_graph.SettingRoute
import com.example.birthdayboom.ui.providers.LocalNavigationProvider
import com.example.birthdayboom.ui.providers.LocalThemeProvider

@Composable
fun AppBottomBar() {
    val navController = LocalNavigationProvider.current
    val themeColor = LocalThemeProvider.current
    val navigationItems = remember { bottomBarList }

    BottomNavigation(
        backgroundColor = themeColor.primaryColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        navigationItems.forEach { screen ->
            val selected = remember(currentDestination){
                currentDestination?.hierarchy?.any { it.route == screen.route::class.qualifiedName } == true
            }

            BottomNavigationItem(
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(screen.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) {
                            ImageVector.vectorResource(screen.iconSelected)
                        } else {
                            ImageVector.vectorResource(screen.iconUnSelected)
                        },
                        contentDescription = "icon"
                    )
                },
                label = {
                    Text(text = screen.title)
                }
            )
        }
    }
}

data class BottomNavigationDestinations<T>(
    val title: String,
    val iconSelected: Int,
    val iconUnSelected: Int,
    val route: T
)

val bottomBarList = listOf(
    BottomNavigationDestinations(
        title = "Birthdays",
        iconSelected = R.drawable.filled_cake_24,
        iconUnSelected = R.drawable.outline_cake_24,
        route = BirthdayGraphRoute
    ),
    BottomNavigationDestinations(
        title = "Contacts",
        iconSelected = R.drawable.filled_contact_24,
        iconUnSelected = R.drawable.outline_contact_24,
        route = ContactGraphRoute
    ),
    BottomNavigationDestinations(
        title = "Settings",
        iconSelected = R.drawable.filled_settings_24,
        iconUnSelected = R.drawable.outline_settings_24,
        route = SettingRoute
    )
)