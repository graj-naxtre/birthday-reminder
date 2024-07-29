package com.example.birthdayboom.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.birthdayboom.R

@Composable
fun OldNotifyMe(setShowDialog: (Boolean) -> Unit, onSave: (String, String, String) -> Unit) {
    var openDatePicker by remember { mutableStateOf(false) }
    var openTimePicker by remember { mutableStateOf(false) }

    var selectedHour by remember { mutableIntStateOf(0) }
    var selectedMinute by remember { mutableIntStateOf(0) }
    var selectedDate by remember { mutableStateOf("Date") }

    var noteText by remember { mutableStateOf("") }

    if (openDatePicker) {
        DateDialog(showDialog = { openDatePicker = it }, saveDate = { selectedDate = it })
    }

    if (openTimePicker) {
        TimerDialog(
            showDialog = { openTimePicker = it },
            changeHour = { selectedHour = it },
            changeMinute = { selectedMinute = it })
    }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(shape = RoundedCornerShape(16.dp), color = Color.White) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colorResource(id = R.color.blue))
                        .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                ) {
                    Text(text = "Notify Me", fontSize = 24.sp, color = Color.White)
                }

                Column(
                    modifier = Modifier.padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 12.dp,
                        bottom = 20.dp
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(bottom = 40.dp)
                        ) {
                            CustomTextField(
                                label = "Date",
                                enabled = false,
                                value = selectedDate,
                                onChange = {},
                                onClick = { openDatePicker = true }
                            )
                            CustomTextField(
                                label = "Time",
                                enabled = false,
                                value = "${selectedHour}:${selectedMinute}",
                                onChange = {},
                                onClick = { openTimePicker = true }
                            )
                            CustomTextField(
                                label = "Note",
                                value = noteText,
                                onChange = { noteText = it },
                                maxLines = 4,
                                onClick = {}
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.DarkGray,
                            fontSize = 16.sp,
                            modifier = Modifier.clickable { setShowDialog(false) })
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Save",
                            color = colorResource(id = R.color.blue),
                            fontSize = 16.sp,
                            modifier = Modifier.clickable {
                                setShowDialog(false)
                                onSave(
                                    selectedDate,
                                    selectedHour.toString(),
                                    selectedMinute.toString()
                                )
                            })
                    }
                }
            }
        }
    }
}

@Composable
fun CustomTextField(
    enabled: Boolean = true,
    label: String,
    value: String,
    onChange: (String) -> Unit,
    maxLines: Int = 1,
    trailingIcon: (@Composable() () -> Unit)? = null,
    onClick: () -> Unit,
) {
    TextField(
        label = { Text(text = label) },
        value = value,
        enabled = enabled,
        onValueChange = { onChange(it) },
        minLines = maxLines,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.DarkGray,
            backgroundColor = colorResource(id = R.color.light_grey),
            focusedIndicatorColor = Color.Unspecified,
            unfocusedIndicatorColor = Color.Unspecified
        ),
        trailingIcon = trailingIcon,
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    )
}