package com.example.birthdayboom.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.birthdayboom.ui.navigations.contact_graph.AddContactRoute
import com.example.birthdayboom.ui.providers.LocalComponentDisplay
import com.example.birthdayboom.ui.providers.LocalNavigationProvider

@Composable
fun FloatingAddContactButton() {
    val componentState = LocalComponentDisplay.current
    val navController = LocalNavigationProvider.current

    AnimatedVisibility(
        visible = componentState.showFloatingActionButton,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        FloatingActionButton(
            onClick = {
                navController.navigate(AddContactRoute)
            },
            shape = MaterialTheme.shapes.large,
        ) {
            Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                Text(text = "Add Contact")
            }
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = text)
    }
}