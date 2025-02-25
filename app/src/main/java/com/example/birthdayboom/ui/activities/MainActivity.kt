package com.example.birthdayboom.ui.activities

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.birthdayboom.ui.navigations.AppBottomNavigation
import com.example.birthdayboom.ui.navigations.AppNavigation
import com.example.birthdayboom.ui.navigations.BottomNavigationDestinations
import com.example.birthdayboom.ui.screens.contact.components.BottomSheetMenu
import com.example.birthdayboom.ui.state.LocalComponentDisplay
import com.example.birthdayboom.ui.theme.BirthdayBoomTheme
import com.example.birthdayboom.utils.permission.PermissionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val permissionManager by lazy { PermissionManager() }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val componentState = LocalComponentDisplay.current

            val navController = rememberNavController()
            val bottomNavigationItems = remember {
                listOf(
                    BottomNavigationDestinations.Birthdays,
                    BottomNavigationDestinations.Contacts,
                    BottomNavigationDestinations.Settings
                )
            }

            BirthdayBoomTheme {
                // A surface container using the 'background' color from the theme
                Box(modifier = Modifier.fillMaxSize()) {
                    val permissionState = permissionManager.permissionState.collectAsState()
                    val permissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { granted ->
                            if (!granted) {
                                Toast.makeText(
                                    context,
                                    "Permission is required in order to notify you",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })

                    LaunchedEffect(permissionState) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }

                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize(),
                        containerColor = Color.Black.copy(alpha = 0.8f),
                        contentColor = Color.White,
                        floatingActionButton = {
                            AnimatedVisibility(
                                visible = componentState.showFloatingActionButton,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                FloatingActionButton(
                                    onClick = {},
                                    shape = MaterialTheme.shapes.large,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                ) {
                                    Text(text = "Add Contact")
                                }
                            }
                        },
                        bottomBar = {
                            AnimatedVisibility(
                                visible = componentState.showBottomBar,
                                enter = slideInVertically { it },
                                exit = slideOutVertically { it }) {
                                AppBottomNavigation(
                                    navController = navController,
                                    navigationItems = bottomNavigationItems
                                )
                            }
                        }
                    ) { paddingValues ->
                        Box(modifier = Modifier.padding(paddingValues)) {
                            AppNavigation(navController)
                        }
                    }
                    BottomSheetMenu(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        isDisplayed = componentState.showBottomMenu,
                        onDismissClick = {
                            componentState.displayBottomSheet(false)
                            componentState.displayBottomBar(true)
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        permissionManager.isNotificationPermissionGranted(this)
    }
}


// make call and message to actual phone number
// search by name functionality
// list all birthday months in order [rotate elements by D position]
// post notification (un-dismissible)

// onCardClick open drawer
// export and import data
// add image to profiles
