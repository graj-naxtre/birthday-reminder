package com.example.birthdayboom.data.database.mappers

import com.example.birthdayboom.data.database.entity.BirthdayEntity
import com.example.birthdayboom.data.database.models.UIBirthdayData
import javax.inject.Inject

class BirthdayBiMapper @Inject constructor(): BiMapper<BirthdayEntity, UIBirthdayData> {

    override fun convert(source: BirthdayEntity): UIBirthdayData {
        return UIBirthdayData(
        contactId = source.contactId,
        name = source.name,
        mobileNumber = source.mobileNumber,
        birthdate = source.birthdate,
        reminderTime = source.reminderTime,
        note = source.note ?: "Happy Birthday"
        )
    }

    override fun convert(source: UIBirthdayData): BirthdayEntity {
        return BirthdayEntity(
            contactId = source.contactId ?: 0,
            name = source.name,
            mobileNumber = source.mobileNumber,
            birthdate = source.birthdate,
            reminderTime = source.reminderTime,
            note = source.note
        )
    }
}