package com.example.birthdayboom.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.birthdayboom.utils.getMillisToDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(showDialog: (Boolean) -> Unit, saveDate: (String) -> Unit) {
    val dateState = rememberDatePickerState()
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
            DatePicker(state = dateState)
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
                    saveDate(getMillisToDate(dateState.selectedDateMillis))
                }) {
                    Text(text = "Confirm")
                }
            }
        }
    }
}