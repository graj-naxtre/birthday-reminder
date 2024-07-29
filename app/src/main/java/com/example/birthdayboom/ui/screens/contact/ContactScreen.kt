package com.example.birthdayboom.ui.screens.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.birthdayboom.R
import com.example.birthdayboom.data.database.models.UIBirthdayData
import com.example.birthdayboom.ui.components.BirthdayCard
import com.example.birthdayboom.ui.components.OldNotifyMe
import com.example.birthdayboom.ui.navigations.navgraph.ContactNavigationEvent
import com.example.birthdayboom.ui.screens.contact.components.ContactHeader


@Composable
fun ContactScreen(
    viewModel: ContactViewModel = hiltViewModel(),
    handleNavigationAction: (ContactNavigationEvent) -> Unit
) {
    val openNotifyMe = remember { mutableStateOf(false) }

    if (openNotifyMe.value) {
        OldNotifyMe(
            setShowDialog = { openNotifyMe.value = it },
            onSave = { date, hour, minute -> viewModel.notifyUser(date, hour, minute) })
    }

    val searchField = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ContactHeader(searchField = searchField.value, onChange = { searchField.value = it })

        LaunchedEffect(true) {
            viewModel.fetchAllContacts()
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(start = 15.dp, end = 15.dp, bottom = 100.dp)
        ) {
            items(
                items = viewModel.allBirthdayContacts.value,
                key = { item: UIBirthdayData -> item.contactId!! }) { item: UIBirthdayData ->
                BirthdayCard(
                    item = item,
                    setShowDialog = { openNotifyMe.value = it },
                    onCardClick = {
                        handleNavigationAction(
                            ContactNavigationEvent.OnProfileClick(item.contactId!!)
                        )
                    })
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .zIndex(10f), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(
            onClick = { handleNavigationAction(ContactNavigationEvent.OnAddContactClick) },
            shape = RoundedCornerShape(16.dp),
            backgroundColor = colorResource(id = R.color.blue),
            contentColor = Color.White,
            modifier = Modifier.padding(bottom = 100.dp, end = 15.dp)
        ) {
            Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                Text(text = "Add Contact")
            }
        }
    }
}