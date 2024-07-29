package com.example.birthdayboom.ui.activities

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.birthdayboom.ui.navigations.AppBottomNavigation
import com.example.birthdayboom.ui.navigations.AppNavigation
import com.example.birthdayboom.ui.navigations.BottomNavigationDestinations
import com.example.birthdayboom.ui.theme.BirthdayBoomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { granted ->
                    if (!granted) {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                    }
                })

            BirthdayBoomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = Color.White
                ) {
                    BirthdayBoomApp()
                }
            }
            if(!checkPermission()){
                permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun checkPermission() : Boolean {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        }
        return true
    }
}

@Composable
fun BirthdayBoomApp() {
    val navController = rememberNavController()
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationDestinations.Birthdays,
            BottomNavigationDestinations.Contacts,
            BottomNavigationDestinations.Settings
        )
    }

    Box {
        AppNavigation(navController)
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            AppBottomNavigation(
                navController = navController,
                navigationItems = bottomNavigationItems
            )
        }
    }
}


// make call and message to actual phone number
// search by name functionality
// list all birthday months in order [rotate elements by D position]

// post notification (un-dismissible)
// export and import data
// add image to profiles
