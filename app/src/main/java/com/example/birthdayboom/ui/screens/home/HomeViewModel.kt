package com.example.birthdayboom.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdayboom.data.repositories.BirthdayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val birthdayRepository: BirthdayRepository,
) : ViewModel() {
    private var collectionJob: Job? = null

    private val _allBirthdayContacts = MutableStateFlow<List<BirthdayWithMonthTitle>>(emptyList())
    val allBirthdayContacts = _allBirthdayContacts.asStateFlow()

    init {
        initializeBirthdays()
    }

    private fun initializeBirthdays() {
        collectionJob?.cancel()

        collectionJob = viewModelScope.launch {
            birthdayRepository.fetchAllBirthdays().collect { list ->
                val newResult = mapBirthdayWithMonthTitle(list = list)
                _allBirthdayContacts.update { newResult }
            }
        }
    }

    private fun mapBirthdayWithMonthTitle(list: List<BirthdayCardInfo>): List<BirthdayWithMonthTitle> {
        var currentMonth = -1
        val result = mutableListOf<BirthdayWithMonthTitle>()

        for (birthday in list) {
            if (birthday.dateUsedForSorting.monthValue != currentMonth) {
                currentMonth = birthday.dateUsedForSorting.monthValue
                result.add(BirthdayWithMonthTitle.Title(text = birthday.dateUsedForSorting.month.name))
            }
            result.add(BirthdayWithMonthTitle.Item(data = birthday))
        }

        return result
    }
}