package com.example.birthdayboom.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.birthdayboom.R

@Preview(showSystemUi = true)
@Composable
fun UpcomingBirthdayCard() {
    Row {
        //Image
        Card(shape = CircleShape) {
            Image(painter = painterResource(id = 0), contentDescription = "profile image")
        }

        //Details
        Column {
            Text(text = "Cara Smith")
            Text(text = "Feb. 29 in 41 days")
        }

        //Notify Me
        Card(shape = CircleShape) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.outline_cake_24),
                contentDescription = "notify me icon"
            )
        }
    }
}
