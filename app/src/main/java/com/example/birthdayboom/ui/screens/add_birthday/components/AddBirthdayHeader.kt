package com.example.birthdayboom.ui.screens.add_birthday.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.birthdayboom.R

@Composable
fun AddBirthdayHeader(modifier: Modifier = Modifier, onBackPress: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.light_blue))
            .statusBarsPadding()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onBackPress() }) {
            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null, tint = Color.Black)
        }
        Text(
            text = "Add Birthday Contact",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}