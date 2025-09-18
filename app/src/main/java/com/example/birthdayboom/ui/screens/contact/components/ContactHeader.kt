package com.example.birthdayboom.ui.screens.contact.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.birthdayboom.ui.providers.LocalThemeProvider

@Composable
fun ContactSearchHeader(value: String, onValueChange: (String) -> Unit) {
    val currentTheme = LocalThemeProvider.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = currentTheme.primaryColor)
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            shape = CircleShape,
            placeholder = { Text(text = "Search") },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.White
            ),
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .background(color = Color.Black, shape = CircleShape)
                        .padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}