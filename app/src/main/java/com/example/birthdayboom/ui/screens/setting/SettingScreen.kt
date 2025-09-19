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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.birthdayboom.ui.providers.LocalThemeProvider

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
                title = "Export Data",
                text = "convert into CSV format and save",
                onClick = {
                    viewModel.exportData { intent ->
                        exportFileActivityLauncher.launch(intent)
                    }
                }
            )
            SettingsItem(
                title = "Import Data",
                text = "import birthdays using CSV file",
                onClick = {
                    viewModel.importData { intent ->
                        importFileActivityLauncher.launch(intent)
                    }
                }
            )
        }
    }
}

@Composable
fun SettingsHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp)
        )
        HorizontalDivider()
    }
}

@Composable
fun SettingsItem(title: String, text: String, onClick: () -> Unit) {
    val currentTheme = LocalThemeProvider.current

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Box(
                modifier = Modifier.background(
                    color = currentTheme.primaryColor,
                    shape = CircleShape
                ).padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = "calendar",
                    modifier = Modifier.size(30.dp),
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.labelMedium)
                Text(text = text, style = MaterialTheme.typography.labelSmall)
            }
            Box {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = "arrow",
                    modifier = Modifier.size(30.dp),
                )
            }
        }
        HorizontalDivider(color = currentTheme.dividerColor)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsItem(){
    SettingsItem(title = "Export Contacts", text = "convert into CSV format and save") { }
}

