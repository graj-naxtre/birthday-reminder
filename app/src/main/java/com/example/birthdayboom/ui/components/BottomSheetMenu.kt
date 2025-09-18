package com.example.birthdayboom.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetMenu(
    isDisplayed: Boolean,
    modifier: Modifier = Modifier,
    onDismissClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isDisplayed,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        BackHandler(onBack = onDismissClick)

        Spacer(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures {
                        onDismissClick()
                    }
                }
                .background(Color.Black.copy(alpha = 0.6f))
                .fillMaxSize()
        )
    }

    AnimatedVisibility(
        visible = isDisplayed,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = modifier
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Close BottomSheet",
                    tint = Color.White
                )
            }
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStartPercent = 30, topEndPercent = 30))
                    .background(color = Color.Black.copy(alpha = 0.8f))
                    .padding(vertical = 30.dp)
                    .navigationBarsPadding(),
            ) {
                BottomSheetItem(onClick = {}, text = "Edit")
                BottomSheetItem(onClick = {}, text = "Delete")
            }
        }
    }
}

@Composable
fun BottomSheetItem(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 30.dp, vertical = 10.dp)
    ) {
        Text(text = text, color = Color.White)
    }
}