package com.example.birthdayboom.ui.screens.contact.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.birthdayboom.ui.providers.LocalThemeProvider

@Composable
fun ContactCard(
    modifier: Modifier = Modifier,
    data: ContactCardInfo,
    onClick: () -> Unit,
) {
    val currentTheme = LocalThemeProvider.current

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 10.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = currentTheme.cardColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .border(
                    width = 2.dp,
                    color = currentTheme.dividerColor,
                    shape = MaterialTheme.shapes.large
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.25f)
                    .background(color = currentTheme.primaryColor)
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = data.initials,
                    color = currentTheme.onPrimaryColor
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = data.name,
                    color = currentTheme.textPrimaryColor,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = data.date,
                    color = currentTheme.textSecondaryColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

data class ContactCardInfo(
    val contactId: Int,
    val name: String,
    val initials: String,
    val date: String,
)