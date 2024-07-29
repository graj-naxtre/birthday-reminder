package com.example.birthdayboom.ui.components

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
import com.example.birthdayboom.ui.navigations.navgraph.BirthdayNavigationEvent
import java.util.Locale

@Composable
fun BirthdayCard(
    item: UIBirthdayData,
    setShowDialog: (Boolean) -> Unit,
    onCardClick: (BirthdayNavigationEvent) -> Unit
) {
    var initialLetters: String by remember { mutableStateOf("") }
    var birthMonth: String by remember { mutableStateOf("") }
    var birthDate: String by remember { mutableStateOf("") }
    LaunchedEffect(true) {
        initialLetters = item.name.trim().split(" ").map { it[0] }.joinToString(".")
        val monthName = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July",
            "August", "September", "October", "November", "December"
        )

        birthMonth = item.birthdate.split("-")[1].let { monthIndex: String ->
            monthName[monthIndex.toInt() - 1]
        }
        birthDate = item.birthdate.split("-")[0]
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(15.dp)
            .then(
                remember {
                    Modifier.clickable { onCardClick(BirthdayNavigationEvent.OnProfileClick(item.contactId!!)) }
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
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
                Text(text = initialLetters.uppercase(Locale.ROOT))
            }
        }

        Column(modifier = Modifier.weight(2f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(text = item.name, fontSize = 20.sp, color = Color.Black)
            Text(text = "$birthMonth $birthDate", fontSize = 14.sp, color = Color.DarkGray)
        }

        IconButton(
            onClick = { setShowDialog(true) }, modifier = Modifier
                .clip(CircleShape)
                .background(colorResource(id = R.color.light_blue))
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.notifications_active_24),
                contentDescription = "notify icon",
                tint = Color.DarkGray
            )
        }
    }
}