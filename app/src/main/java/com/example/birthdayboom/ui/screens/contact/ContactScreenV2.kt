package com.example.birthdayboom.ui.screens.contact

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.birthdayboom.R
import com.example.birthdayboom.data.database.models.UIBirthdayData
import com.example.birthdayboom.ui.navigations.navgraph.ContactNavigationEvent
import com.example.birthdayboom.ui.screens.birthday.components.ContactCard
import com.example.birthdayboom.ui.screens.contact.components.ContactHeader
import com.example.birthdayboom.ui.screens.profile.ProfileScreenV2
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ContactScreenV2(
    handleNavigationAction: (ContactNavigationEvent) -> Unit,
    viewModel: ContactViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerData = remember {
        mutableStateOf(UIBirthdayData())
    }

    val scope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf("") }

    val contacts by viewModel.allBirthdayContacts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllContacts()
    }

    LaunchedEffect(searchText) {
        if (searchText.isNotEmpty()) {
            delay(1500)
            Log.d("SearchField", searchText)
            viewModel.searchByContactName(searchText)
        } else {
            viewModel.showAllContacts()
        }
    }
// Calendar.getInstance().get(Calendar.YEAR) - drawerData.value.birthdate.split("-")[2].toInt()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                ProfileScreenV2(
                    data = drawerData.value,
                    age = drawerData.value.birthdateString,
                    onClose = { scope.launch { drawerState.close() } }
                )
            }
        }
    ) {
        Scaffold(topBar = {
            ContactHeader(searchField = searchText) {
                searchText = it
            }
        },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { handleNavigationAction(ContactNavigationEvent.OnAddContactClick) },
                    shape = RoundedCornerShape(16.dp),
                    containerColor = colorResource(id = R.color.blue),
                    contentColor = Color.White,
                    modifier = Modifier.padding(bottom = 100.dp, end = 15.dp)
                ) {
                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text(text = "Add Contact")
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
            ) {
                LazyColumn(contentPadding = PaddingValues(bottom = 100.dp)) {
                    item {
                        ContactCard(
                            cardClick = { scope.launch { drawerState.open() } },
                            notificationClick = {  })
                    }
                    items(items = contacts) {
                        ContactCard(
                            title = it.name,
                            subtitle = it.birthdateString,
                            cardClick = {
                                drawerData.value = it
                                scope.launch { drawerState.open() }
                            }
                        )
                    }
                }
            }
        }
    }
}