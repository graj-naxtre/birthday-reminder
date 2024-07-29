package com.example.birthdayboom.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerDialog(showDialog:(Boolean) -> Unit, changeHour: (Int)-> Unit, changeMinute: (Int) -> Unit){
    var selectedHour by remember { mutableIntStateOf(0) }
    var selectedMinute by remember { mutableIntStateOf(0) }
    val timeState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute,
        is24Hour = true
    )

    AlertDialog(
        onDismissRequest = { showDialog(false) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimePicker(state = timeState)
            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { showDialog(false) }) {
                    Text(text = "Dismiss")
                }
                TextButton(onClick = {
                    showDialog(false)
//                    selectedHour = timeState.hour
                    changeHour(timeState.hour)
//                    selectedMinute = timeState.minute
                    changeMinute(timeState.minute)
                }) {
                    Text(text = "Confirm")
                }
            }
        }
    }
}