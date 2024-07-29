package com.example.birthdayboom.ui.screens.add_birthday

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.benlu.composeform.fields.DateField
import ch.benlu.composeform.fields.TextField
import ch.benlu.composeform.formatters.dateShort
import com.example.birthdayboom.R
import com.example.birthdayboom.ui.components.TimerDialog
import com.example.birthdayboom.ui.navigations.navgraph.ContactNavigationEvent
import com.example.birthdayboom.ui.screens.contact.ContactViewModel

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBirthdayScreen(viewModel: ContactViewModel = hiltViewModel(), handleNavigationAction : (ContactNavigationEvent) -> Unit) {
    var selectedHour by remember { mutableIntStateOf(0) }
    var selectedMinute by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        TimerDialog(showDialog = {showDialog = it}, changeHour = { selectedHour = it }, changeMinute = { selectedMinute = it })
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.light_blue))
                .padding(top = 30.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
        ) {
            Text(text = "Add Birthday Contact", fontSize = 34.sp, color = Color.DarkGray)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 40.dp, start = 15.dp, end = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            //name
            TextField(
                label = "Contact Name",
                form = viewModel.form,
                fieldState = viewModel.form.name,
            ).Field()

            //birthdate
            DateField(
                label = "Date of Birth",
                form = viewModel.form,
                fieldState = viewModel.form.birthdate,
                formatter = ::dateShort
            ).Field()

            //mobile no
            TextField(
                label = "Contact Number",
                form = viewModel.form,
                fieldState = viewModel.form.mobileNumber,
                keyboardType = KeyboardType.Phone
            ).Field()

            //reminder time
            OutlinedTextField(
                enabled = false,
                value = "${selectedHour}:${selectedMinute}",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.Black)
                    .background(Color.White)
                    .clickable { showDialog = true })

            //note
            TextField(
                label = "Note",
                form = viewModel.form,
                fieldState = viewModel.form.note
            ).Field()

            //Button
            Button(
                onClick = {
                    viewModel.addContact("${selectedHour}:${selectedMinute}");
                    handleNavigationAction(ContactNavigationEvent.OnSaveContactClick)
                          },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
            ) {
                Text(text = "Save", color = Color.White)
            }
        }
    }
}
*/