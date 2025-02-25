package com.example.birthdayboom.ui.screens.setting

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingScreen(viewModel: SettingViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var selectedUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val exportFileActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.data?.also { uri ->
                    viewModel.moveData(uri)
                }
            }
        })

    val importFileActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.data?.also { uri ->
                    viewModel.extractFile(uri, context) { fileName ->
                        viewModel.processFile(fileName)
                    }
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SettingsHeader()

        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SettingsItem(
                text = "Export Data",
                onClick = {
                    viewModel.exportData { intent ->
                        exportFileActivityLauncher.launch(intent)
                    }
                }
            )
            SettingsItem(
                text = "Import Data",
                onClick = {
                    viewModel.importData { intent ->
                        importFileActivityLauncher.launch(intent)
                    }
                }
            )
            selectedUri?.let {
                Text(text = "$it")
            }
        }
    }
}

@Composable
fun SettingsHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black)
            .statusBarsPadding()
            .padding(20.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun SettingsItem(text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .then(remember {
                Modifier.clickable { onClick() }
            })
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.large
            )
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Box {
            Icon(
                imageVector = Icons.Outlined.DateRange,
                contentDescription = "calendar",
                modifier = Modifier.size(30.dp),
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            Text(text = text)
        }
        Box {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = "arrow",
                modifier = Modifier.size(30.dp),
            )
        }
    }
}