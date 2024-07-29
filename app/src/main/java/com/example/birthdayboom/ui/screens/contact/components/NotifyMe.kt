package com.example.birthdayboom.ui.screens.contact.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.birthdayboom.R
import com.example.birthdayboom.data.database.models.UIBirthdayData
import com.example.birthdayboom.ui.screens.add_birthday.components.FormTextField

@Composable
fun NotifyMeDialog(onDismiss: () -> Unit, onConfirm: () -> Unit, data: UIBirthdayData) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(shape = RoundedCornerShape(16.dp), color = Color.White) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colorResource(id = R.color.blue))
                        .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 20.dp)
                ) {
                    Text(
                        text = "Notify Me",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    FormTextField(
                        placeholder = "Date",
                        value = data.birthdate,
                        onChange = {},
                        readOnly = true
                    )
                    FormTextField(
                        placeholder = "Time",
                        value = data.reminderTime,
                        onChange = {},
                        readOnly = false
                    )
                    FormTextField(
                        placeholder = "Note",
                        value = data.note,
                        onChange = {},
                        readOnly = true,
                        minLines = 3
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.End)
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = "CANCEL", color = Color.Gray)
                    }
                    TextButton(onClick = { onConfirm() }) {
                        Text(text = "SAVE", color = colorResource(id = R.color.blue))
                    }
                }
            }
        }
    }
}