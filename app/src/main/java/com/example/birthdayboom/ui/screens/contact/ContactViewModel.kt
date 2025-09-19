package com.example.birthdayboom.ui.screens.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdayboom.data.database.entity.BirthdayEntity
import com.example.birthdayboom.data.repositories.BirthdayRepository
import com.example.birthdayboom.ui.screens.contact.components.ContactCardInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val birthdayRepository: BirthdayRepository,
) : ViewModel() {

    private var collectionJob: Job? = null

    private val _allBirthdayContacts = MutableStateFlow<List<ContactCardInfo>>(emptyList())
    val allBirthdayContacts = _allBirthdayContacts.asStateFlow()

    private val completeContactsList = MutableStateFlow<List<ContactCardInfo>>(emptyList())

    init {
        initializeContacts()
        setMockData()
    }

    private fun setMockData(){
        viewModelScope.launch {
            val list = getSampleBirthdayEntities()
            birthdayRepository.setMockData(mockContacts = list)
        }
    }

    private fun initializeContacts(){
        collectionJob?.cancel()

        collectionJob = viewModelScope.launch {
            birthdayRepository.fetchAllContacts().collect { list ->
                completeContactsList.value = list

                _allBirthdayContacts.update { list }
            }
        }
    }

    fun searchByContactName(searchText: String) {
        if(searchText.isNotEmpty()){
            val filteredContacts = completeContactsList.value.filter {
                it.name.startsWith(searchText)
            }

            _allBirthdayContacts.update {
                filteredContacts
            }
        } else {
            initializeContacts()
        }
    }

    private fun getSampleBirthdayEntities(): List<BirthdayEntity> {
        val today = LocalDate.now()

        val todayBirthday = BirthdayEntity(
            contactId = 0,
            name = "Alice",
            mobileNumber = "1234567890",
            birthdate = today,
            birthdayMonth = today.monthValue,
            note = "Friend from school"
        )

        val todayBirthday2 = BirthdayEntity(
            contactId = 0,
            name = "Bob",
            mobileNumber = "9876543210",
            birthdate = today,
            birthdayMonth = today.monthValue,
            note = "Colleague"
        )

        val todayBirthday3 = BirthdayEntity(
            contactId = 0,
            name = "Charlie",
            mobileNumber = "1122334455",
            birthdate = today,
            birthdayMonth = today.monthValue,
            note = "Neighbor"
        )

        val randomBirthday1 = BirthdayEntity(
            contactId = 0,
            name = "David",
            mobileNumber = "6677889900",
            birthdate = LocalDate.of(1995, 5, 20),
            birthdayMonth = 5,
            note = "Best friend"
        )

        val randomBirthday2 = BirthdayEntity(
            contactId = 0,
            name = "Eva",
            mobileNumber = "4455667788",
            birthdate = LocalDate.of(1988, 7, 15),
            birthdayMonth = 7,
            note = "Family friend"
        )

        // Return the list of 5 BirthdayEntities
        return listOf(todayBirthday, todayBirthday2, todayBirthday3, randomBirthday1, randomBirthday2)
    }


}
