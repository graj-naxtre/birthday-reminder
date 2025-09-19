package com.example.birthdayboom.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.birthdayboom.ui.components.FloatingAddContactButton
import com.example.birthdayboom.ui.navigations.root.AppBottomBar
import com.example.birthdayboom.ui.navigations.root.AppNavigation
import com.example.birthdayboom.ui.navigations.root.PermissionChip
import com.example.birthdayboom.ui.providers.LocalComponentDisplay
import com.example.birthdayboom.ui.providers.LocalNavigationProvider
import com.example.birthdayboom.ui.providers.LocalThemeProvider
import com.example.birthdayboom.ui.providers.darkThemeColor
import com.example.birthdayboom.ui.providers.lightThemeColor
import com.example.birthdayboom.ui.components.BottomSheetMenu
import com.example.birthdayboom.utility.permission.IPermissionDelegate
import com.example.birthdayboom.utility.permission.PermissionDelegate
import com.example.birthdayboom.utility.permission.PermissionState
import com.example.birthdayboom.utility.toast_holder.ToastHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), IPermissionDelegate by PermissionDelegate() {

    private var notificationPermissionState by mutableStateOf(PermissionState.GRANTED)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val componentState = LocalComponentDisplay.current

            val currentTheme = if (isSystemInDarkTheme()) darkThemeColor else lightThemeColor
            val navController = rememberNavController()

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
                }
            )

            LaunchedEffect(ToastHolder.toastMessage) {
                if (ToastHolder.toastMessage.isNotBlank()) {
                    Toast.makeText(this@MainActivity, ToastHolder.toastMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            CompositionLocalProvider(
                LocalNavigationProvider provides navController,
                LocalThemeProvider provides currentTheme
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = currentTheme.backgroundColor,
                        contentColor = currentTheme.textPrimaryColor,
                        floatingActionButton = {
                            FloatingAddContactButton()
                        },
                        bottomBar = {
                            AnimatedVisibility(
                                visible = componentState.showBottomBar,
                                enter = slideInVertically { it },
                                exit = slideOutVertically { it }
                            ) {
                                Column {
                                    if (notificationPermissionState != PermissionState.GRANTED) {
                                        PermissionChip(
                                            onClick = {
                                                if (notificationPermissionState == PermissionState.REDIRECT_SETTINGS) {
                                                    redirectToSettings(this@MainActivity)
                                                } else {
                                                    permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                                                }
                                            }
                                        )
                                    }
                                    AppBottomBar()
                                }
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

        scheduleDailyWorker(activity = this)
    }

    override fun onResume() {
        super.onResume()
        notificationPermissionState = isNotificationPermissionGranted(this)
    }
}


// make call and message to actual phone number
// search by name functionality
// list all birthday months in order [rotate elements by D position]
// post notification (un-dismissible)

// onCardClick open drawer
// export and import data
// add image to profiles
