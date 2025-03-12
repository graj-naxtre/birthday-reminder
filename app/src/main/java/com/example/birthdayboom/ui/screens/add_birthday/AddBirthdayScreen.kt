package com.example.birthdayboom.ui.screens.add_birthday

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.birthdayboom.ui.components.AnimatedTextField
import com.example.birthdayboom.ui.components.PrimaryTextField
import com.example.birthdayboom.utils.getTodayDate

@Preview(showSystemUi = true)
@Composable
fun AddBirthdayScreen() {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var birthdayNote by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedTextField(
            value = name,
            onValueChange = { name = it },
            label = "Name",
            placeholder = "Enter Name",
            errorText = "Name is required field.",
        )
        PrimaryTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = "Phone Number",
            placeholder = "Enter Phone Number",
            errorText = "Phone Number is required field.",
        )
        PrimaryTextField(
            value = dob,
            onValueChange = { dob = it },
            label = "Date of Birth",
            placeholder = getTodayDate(),
            errorText = "Date is required field.",
        )
        PrimaryTextField(
            value = birthdayNote,
            onValueChange = { birthdayNote = it },
            label = "Birthday Note",
            placeholder = "Happy Birthday ${if (name.isNotBlank()) name else "Person's name !!"}",
            errorText = "Birthday note is required field.",
            maxLines = 4
        )
    }
}