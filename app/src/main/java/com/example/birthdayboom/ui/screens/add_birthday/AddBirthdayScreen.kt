package com.example.birthdayboom.ui.screens.add_birthday

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.birthdayboom.ui.components.PrimaryButton
import com.example.birthdayboom.ui.components.PrimaryTextField
import com.example.birthdayboom.ui.providers.LocalNavigationProvider
import com.example.birthdayboom.utils.getTodayDate
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun AddBirthdayScreen(viewModel: AddBirthdayViewModel = hiltViewModel()) {
    val navController = LocalNavigationProvider.current

    var showDateDialog by remember { mutableStateOf(false) }
    val dateState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val day = Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneId.of("UTC"))
                .toLocalDate()
            return day <= LocalDate.now()
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        PrimaryTextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = "Name",
            placeholder = "Enter Name",
            errorText = "Name is required field.",
        )
        PrimaryTextField(
            value = viewModel.phoneNumber,
            onValueChange = { viewModel.phoneNumber = it },
            label = "Phone Number",
            placeholder = "Enter Phone Number",
            errorText = "Phone Number is required field.",
        )
        PrimaryTextField(
            value = viewModel.dob,
            onValueChange = {},
            label = "Date of Birth",
            placeholder = getTodayDate(),
            readOnly = true,
            errorText = "Date is required field.",
            onFocusGained = { showDateDialog = true }
        )
        PrimaryTextField(
            value = viewModel.birthdayNote,
            onValueChange = { viewModel.birthdayNote = it },
            label = "Birthday Note",
            placeholder = "Happy Birthday ${viewModel.name.ifBlank { "Person's name !!" }}",
            errorText = "Birthday note is required field.",
            maxLines = 4
        )

        PrimaryButton(
            text = "SAVE",
            onClick = {
                viewModel.addContact(navController = navController)
            }
        )
    }

    if (showDateDialog) {
        DatePickerDialog(
            onDismissRequest = { showDateDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showDateDialog = false
                        viewModel.dob = getFormattedDate(dateState.selectedDateMillis)
                    }
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDateDialog = false }
                ) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(
                state = dateState,
                showModeToggle = true
            )
        }
    }
}

fun getFormattedDate(timeInMillis: Long? = null): String {
    val calender = Calendar.getInstance()
    if (timeInMillis != null) {
        calender.timeInMillis = timeInMillis
    }
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    return dateFormat.format(calender.timeInMillis)
}