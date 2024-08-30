package com.example.birthdayboom.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.birthdayboom.ui.components.CustomTextField
import com.example.birthdayboom.ui.screens.contact.ContactViewModel

@Composable
fun ProfileScreen(contactId: Int, viewModel: ContactViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.fetchPersonProfile(contactId)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            CustomTextField(
                label = "Name",
                value = "${viewModel.personProfileDetails?.name}",
                onChange = {},
                maxLines = 1,
                enabled = false,
                onClick = {}
            )
            CustomTextField(
                label = "Birthdate",
                value = "${viewModel.personProfileDetails?.birthdateString}",
                onChange = {},
                maxLines = 1,
                enabled = false,
                onClick = {}
            )
            CustomTextField(
                label = "Mobile Number",
                value = "${viewModel.personProfileDetails?.mobileNumber}",
                onChange = {},
                maxLines = 1,
                enabled = false,
                onClick = {}
            )
            CustomTextField(
                label = "Reminder Time",
                value = "${viewModel.personProfileDetails?.reminderTime}",
                onChange = {},
                maxLines = 1,
                enabled = false,
                onClick = {}
            )
        }
    }
}

