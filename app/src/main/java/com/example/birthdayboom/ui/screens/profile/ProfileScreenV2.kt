package com.example.birthdayboom.ui.screens.profile

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Create
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ProfileScreenV2(onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { onClose() }) {
                Icon(imageVector = Icons.Outlined.Close, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Outlined.Create, contentDescription = null)
            }
        }
        Row (modifier = Modifier.fillMaxWidth()){
            Column (modifier = Modifier.padding(horizontal = 16.dp)){
                ElevatedCard(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(70.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Gray, contentColor = Color.White
                    )
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(text = "S.R")
                    }
                }
            }
            Column (verticalArrangement = Arrangement.spacedBy(20.dp)){
                NameCard()
                DetailField(label = "BIRTHDATE", value = "Jan. 19, 1997")
                DetailField(label = "AGE", value = "27 Years Old")
                DetailField(label = "PHONE NUMBER", value = "6206119968")
            }
        }
    }
}

@Composable
fun DetailField(label: String, value: String) {
    Column {
        Text(text = label, color = Color.Gray, style = MaterialTheme.typography.bodyLarge)
        Text(text = value, color = Color.Black, style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun NameCard() {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = "Satya Raj",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Feb. 29 in 41 days",
            style = MaterialTheme.typography.titleMedium,
            color = Color.DarkGray
        )
    }
}