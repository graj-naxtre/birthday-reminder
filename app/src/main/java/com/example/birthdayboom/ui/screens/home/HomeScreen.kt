package com.example.birthdayboom.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.birthdayboom.ui.providers.LocalThemeProvider

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val birthdayContacts by viewModel.allBirthdayContacts.collectAsState()
    val currentTheme = LocalThemeProvider.current

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
                items(
                    items = birthdayContacts,
                    key = { type ->
                        when (type) {
                            is BirthdayWithMonthTitle.Item -> type.data.contactId
                            is BirthdayWithMonthTitle.Title -> type.text
                        }
                    }
                ) { type ->
                    when (type) {

                        is BirthdayWithMonthTitle.Title -> Text(
                            text = type.text,
                            style = MaterialTheme.typography.titleMedium,
                            color = currentTheme.textPrimaryColor
                        )

                        is BirthdayWithMonthTitle.Item -> BirthdayCard(
                            data = type.data,
                            onClick = {}
                        )
                    }
                }
            }
        }
    }
}