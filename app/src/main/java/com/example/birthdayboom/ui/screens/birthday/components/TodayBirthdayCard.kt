package com.example.birthdayboom.ui.screens.birthday.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.birthdayboom.R

@Composable
fun TodayBirthdayCard(handleCommunication:(Communication) -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ElevatedCard(
            shape = CircleShape,
            modifier = Modifier
                .size(50.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.blue), contentColor = Color.White
            )
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(text = "R.K")
            }
        }
        Column(modifier = Modifier.weight(2f)) {
            Text(
                text = "G Raj Kumar",
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(text = "🎂 turning 50", color = colorResource(id = R.color.blue))
        }
        IconButton(
            onClick = {
                handleCommunication(Communication.MakeCall(number = "6206119968"))
            }, modifier = Modifier
                .clip(CircleShape)
                .background(colorResource(id = R.color.blue))
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.call_24),
                contentDescription = "call icon",
                tint = Color.White
            )
        }
        IconButton(
            onClick = {
                handleCommunication(Communication.SendMessage(number = "6206119968", message = "Happy birthday 🎂"))
            }, modifier = Modifier
                .clip(CircleShape)
                .background(colorResource(id = R.color.blue))
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.message_24),
                contentDescription = "message icon",
                tint = Color.White
            )
        }
    }
}