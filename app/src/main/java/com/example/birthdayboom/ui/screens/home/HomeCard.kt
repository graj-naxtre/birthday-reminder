package com.example.birthdayboom.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate

data class BirthdayCardInfo(
    val contactId: Int,
    val name: String,
    val date: String,
    val dateUsedForSorting: LocalDate
)

@Composable
fun BirthdayCard(
    modifier: Modifier = Modifier,
    data: BirthdayCardInfo,
    onClick: () -> Unit,
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 10.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = data.name, style = MaterialTheme.typography.titleMedium)
                Text(text = data.date, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}