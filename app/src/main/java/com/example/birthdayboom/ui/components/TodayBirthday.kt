package com.example.birthdayboom.ui.components

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.birthdayboom.R
import com.example.birthdayboom.data.database.models.UIBirthdayData
import com.example.birthdayboom.utils.getTodayDate
import com.example.birthdayboom.ui.screens.birthday.BirthdayViewModel
import java.util.Calendar

@Composable
fun TodayBirthday(viewModel: BirthdayViewModel) {
    var today by remember { mutableStateOf("Welcome !") }

    LaunchedEffect(true) {
        val monthName = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July",
            "August", "September", "October", "November", "December"
        )

        val dayName =
            arrayOf("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY")

        today = Calendar.getInstance().let {
            "${dayName[it.get(Calendar.DAY_OF_WEEK)]}, ${monthName[it.get(Calendar.MONTH)]} ${
                it.get(Calendar.DAY_OF_MONTH)
            }, ${
                it.get(Calendar.YEAR)
            }"
        }
        viewModel.isTodayBirthday(getTodayDate())
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.light_blue))
            .padding(start = 10.dp, end = 10.dp, top = 40.dp, bottom = 12.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Text(text = today, color = Color.DarkGray, fontSize = 16.sp)
            Text(text = "Today's Birthday", color = Color.Black, fontSize = 36.sp)
            if (viewModel.isTodayBirthday.value) {
                viewModel.todayBirthdayDetails.value.forEach { item ->
                    MyBirthdayCard(
                        birthdayData = item,
                        sendMessage = { viewModel.sendTextMessage(phoneNumber = it) },
                        makePhoneCall = { viewModel.makePhoneCall(phoneNumber = it) })
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .background(Color.White)
                        .clip(RoundedCornerShape(5.dp)),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "No Birthday 🎂", fontSize = 24.sp, color = Color.DarkGray)
                }
            }
        }
    }
}

@Composable
fun MyBirthdayCard(
    birthdayData: UIBirthdayData? = null,
    sendMessage: (String) -> Unit,
    makePhoneCall: (String) -> Unit
) {
    Row(
        modifier = Modifier
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
            Text(text = birthdayData?.name ?: "No birthday", fontSize = 24.sp, color = Color.Black)
            Text(text = "🎂 turning 50", color = Color.DarkGray)
        }
        IconButton(
            onClick = { makePhoneCall(birthdayData?.mobileNumber ?: "") }, modifier = Modifier
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
            onClick = { sendMessage(birthdayData?.mobileNumber ?: "") }, modifier = Modifier
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