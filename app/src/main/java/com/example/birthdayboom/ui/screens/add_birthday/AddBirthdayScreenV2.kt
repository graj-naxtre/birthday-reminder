package com.example.birthdayboom.ui.screens.add_birthday


import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.birthdayboom.R
import com.example.birthdayboom.ui.navigations.navgraph.ContactNavigationEvent
import com.example.birthdayboom.ui.screens.add_birthday.components.AddBirthdayHeader
import com.example.birthdayboom.ui.screens.add_birthday.components.FormTextField
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBirthdayScreenV2(
    handleNavigationAction: (ContactNavigationEvent) -> Unit,
    viewModel: AddBirthdayViewModel = hiltViewModel()
) {
    // name
    var contactName by remember { mutableStateOf("") }

    //date
    var showDateDialog by remember { mutableStateOf(false) }
    val dateState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val day = Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneId.of("UTC"))
                    .toLocalDate()
                day <= LocalDate.now()
            } else {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.timeInMillis <= utcTimeMillis
            }
        }
    })
    var dateOfBirth by remember {
        mutableStateOf(getFormattedDate())
    }

    // contact number
    var contactNumber by remember {
        mutableStateOf("")
    }

    // time
    var showTimeDialog by remember { mutableStateOf(false) }
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    var reminderTime by remember {
        mutableStateOf("${timePickerState.hour}:${timePickerState.minute}")
    }

    //wish note
    var wishNote by remember {
        mutableStateOf("")
    }

    //supporting text
    var showSupportingText by remember {
        mutableStateOf(false)
    }

    Scaffold(topBar = {
        AddBirthdayHeader(onBackPress = {
            handleNavigationAction(
                ContactNavigationEvent.OnSaveContactClick
            )
        })
    }) { padding ->
        Column (modifier = Modifier.fillMaxSize().padding(padding).background(Color.White)){
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 100.dp)
            ) {
                item {
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        placeholder = "Contact Name",
                        value = contactName,
                        onChange = { contactName = it },
                        readOnly = false,
                        supportingTextCondition = { showSupportingText && contactName.isEmpty() }
                    )
                }
                item {
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        placeholder = "Date of Birth",
                        value = dateOfBirth,
                        onChange = {
                            dateOfBirth = dateState.selectedDateMillis?.let { getFormattedDate(it) }
                                ?: getFormattedDate()
                        },
                        readOnly = true,
                        onClick = { showDateDialog = true }
                    )
                }

                item {
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        placeholder = "Contact Number",
                        value = contactNumber,
                        onChange = { if (it.length <= 10) contactNumber = it },
                        readOnly = false,
                        supportingText = "Must be a 10 digit number",
                        keyboardType = KeyboardType.Number,
                        supportingTextCondition = { showSupportingText && contactNumber.length < 10 }
                    )
                }
                item {
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        placeholder = "Reminder Time",
                        value = reminderTime,
                        onChange = {},
                        readOnly = true,
                        onClick = { showTimeDialog = true }
                    )
                }
                item {
                    FormTextField(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        placeholder = "Wish Note",
                        value = wishNote,
                        onChange = { wishNote = it },
                        readOnly = false,
                        minLines = 4,
                        supportingTextCondition = { showSupportingText && wishNote.isEmpty() }
                    )
                }
                item {
                    Button(
                        onClick = {
                            showSupportingText = true
                            if (contactName.isNotEmpty() && contactNumber.length == 10 && wishNote.isNotEmpty()) {
                                viewModel.addContact(
                                    name = contactName,
                                    mobileNumber = contactNumber,
                                    birthdate = dateOfBirth,
                                    reminderTime = reminderTime,
                                    note = wishNote
                                )
                                handleNavigationAction(ContactNavigationEvent.OnSaveContactClick)
                            }
                        },
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
                        modifier = Modifier.fillMaxWidth(0.85f)
                    ) {
                        Text(text = "SAVE", color = Color.White)
                    }
                }
            }
        }
        if (showDateDialog) {
            Box(modifier = Modifier.fillMaxSize()) {
                DatePickerDialog(
                    onDismissRequest = { showDateDialog = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDateDialog = false
                                dateOfBirth = getFormattedDate(dateState.selectedDateMillis)
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
        if (showTimeDialog) {
            Box(modifier = Modifier.fillMaxSize()) {
                TimePickerDialog(
                    onDismiss = { showTimeDialog = false },
                    onConfirm = {
                        showTimeDialog = false
                        reminderTime = "${timePickerState.hour}:${timePickerState.minute}"
                    }
                ) {
                    TimePicker(
                        state = timePickerState,
                    )
                }
            }
        }
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}

fun getFormattedDate(timeInMillis: Long? = null): String {
    val calender = Calendar.getInstance()
    if (timeInMillis != null) {
        calender.timeInMillis = timeInMillis
    }
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    return dateFormat.format(calender.timeInMillis)
}