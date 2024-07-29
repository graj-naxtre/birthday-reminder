package com.example.birthdayboom.ui.screens.birthday

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.birthdayboom.data.database.models.UIBirthdayData
import com.example.birthdayboom.ui.screens.birthday.components.BirthdayHeader
import com.example.birthdayboom.ui.screens.birthday.components.Communication
import com.example.birthdayboom.ui.screens.birthday.components.ContactCard
import com.example.birthdayboom.ui.screens.contact.components.NotifyMeDialog

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun BirthdayScreenV2(viewModel: BirthdayViewModel = hiltViewModel()) {
    val birthdayList by viewModel.allBirthdays.collectAsState()
    var showNotifyDialog by remember { mutableStateOf(false) }
    var notifyItem by remember {
        mutableStateOf<UIBirthdayData?>(null)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchAllBirthdays()
    }

    Scaffold(topBar = {
        BirthdayHeader(handleCommunication = {
            when (it) {
                is Communication.MakeCall -> viewModel.makePhoneCall(phoneNumber = it.number)
                is Communication.SendMessage -> viewModel.sendWhatsappMessage(
                    message = it.message,
                    phoneNumber = it.number
                )
            }
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(vertical = 20.dp)
                    ) {
                        Text(
                            text = "Upcoming Birthdays",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                birthdayList.map {
                    item {
                        Row(modifier = Modifier.padding(vertical = 10.dp)) {
                            Text(
                                text = it.monthName,
                                color = Color.DarkGray,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    items(items = it.birthdayList) { birthday ->
                        ContactCard(
                            title = birthday.name,
                            subtitle = birthday.birthdate,
                            modifier = Modifier.border(
                                width = 0.5.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(5.dp)
                            ),
                            notificationClick = {
                                showNotifyDialog = true
                                notifyItem = birthday
                            }
                        )
                    }
                }
            }
            if (showNotifyDialog) {
                notifyItem?.let {
                    NotifyMeDialog(
                        onDismiss = { showNotifyDialog = false },
                        onConfirm = { showNotifyDialog = false },
                        data = it
                    )
                }
            }
        }
    }
}