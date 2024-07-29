package com.example.birthdayboom.ui.screens.setting

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.birthdayboom.R

@Composable
fun SettingScreen(viewModel: SettingViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val fileActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
           if(it.resultCode == Activity.RESULT_OK){
               Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show()
           }
        })

    Scaffold(topBar = { SettingsHeader() }) {
        Column(modifier = Modifier.padding(it)) {
            SettingsItem(text = "Export Data", onClick = {
                viewModel.exportData {intent ->
                    fileActivityLauncher.launch(intent)
                }
            })
            SettingsItem(text = "Import Data", onClick = {})
        }
    }
}

@Composable
fun SettingsHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.light_blue))
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun SettingsItem(text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .then(remember {
                Modifier.clickable { onClick() }
            })
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Row(modifier = Modifier.weight(0.2f)) {
            Icon(
                imageVector = Icons.Outlined.DateRange,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            Text(text = text)
        }
        Row(modifier = Modifier.weight(0.2f)) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}