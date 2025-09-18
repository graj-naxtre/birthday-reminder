package com.example.birthdayboom.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val birthdayContacts by viewModel.allBirthdayContacts.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 10.dp,
                    end = 10.dp,
                    bottom = 30.dp,
                    top = 10.dp
                ),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = birthdayContacts, key = { it.contactId }) {
                    BirthdayCard (
                        data = it,
                        onClick = {}
                    )
                }
            }
        }
    }
}