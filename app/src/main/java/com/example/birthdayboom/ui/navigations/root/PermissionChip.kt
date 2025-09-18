package com.example.birthdayboom.ui.navigations.root

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.birthdayboom.R
import com.example.birthdayboom.ui.providers.LocalThemeProvider

@Composable
fun PermissionChip(onClick: () -> Unit) {
    val currentTheme = LocalThemeProvider.current
    val fontSize = remember { 12.sp }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = currentTheme.warningColor)
            .padding(horizontal = 16.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(R.string.notification_permission),
            fontSize = fontSize,
            color = currentTheme.textPrimaryColor
        )
        Text(
            text = "GRANT",
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable(onClick = onClick),
            fontSize = fontSize,
            color = currentTheme.textPrimaryColor
        )
    }
}