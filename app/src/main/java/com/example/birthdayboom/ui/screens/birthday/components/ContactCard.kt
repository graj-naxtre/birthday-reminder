package com.example.birthdayboom.ui.screens.birthday.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.birthdayboom.R

@Composable
fun ContactCard(
    modifier: Modifier = Modifier,
    title: String = "Satya Raj",
    subtitle: String = "Feb. 29 in 41 days",
    cardClick: () -> Unit = {},
    notificationClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .then(
                remember {
                    Modifier.clickable { cardClick() }
                }
            )
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ElevatedCard(
            shape = CircleShape,
            modifier = Modifier
                .size(50.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.light_grey),
                contentColor = Color.DarkGray
            )
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(text = "S.R")
            }
        }

        Column(modifier = Modifier.weight(2f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        }

        IconButton(
            onClick = {
                notificationClick()
            },
            modifier = Modifier
                .clip(CircleShape)
                .background(colorResource(id = R.color.light_grey))
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.notifications_active_24),
                contentDescription = "notify icon",
                tint = Color.DarkGray
            )
        }
    }
}