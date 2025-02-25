package com.example.birthdayboom.ui.screens.contact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.birthdayboom.ui.screens.contact.components.ContactCard
import com.example.birthdayboom.ui.screens.contact.components.ContactSearchHeader
import com.example.birthdayboom.ui.state.LocalComponentDisplay

@Composable
fun ContactScreen() {
    val componentState = LocalComponentDisplay.current
    var input by remember { mutableStateOf("") }

    DisposableEffect(Unit) {
        componentState.displayFloatingButton(value = true)
        onDispose {
            componentState.displayFloatingButton(value = false)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            ContactSearchHeader(value = input, onValueChange = { input = it })
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
                items(count = 10) {
                    ContactCard(onClick = {
                        componentState.displayBottomBar(false)
                        componentState.displayBottomSheet(true)
                    })
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewContactScreen() {
    ContactScreen()
}