package com.example.deliveryonsitebinaufcoffee

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickupTimeScreen(
    onBackClick: () -> Unit,
    onConfirm: (selectedDay: String, selectedTime: String) -> Unit
) {
    var selectedDay by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

    val timeSlots = listOf(
        "10.00 - 10.30",
        "11.00 - 11.30",
        "13.00 - 13.30",
        "16.00 - 17.00"
    )
    val calendar = Calendar.getInstance()
    val today = calendar.get(Calendar.DAY_OF_MONTH)
    val todayMonth = SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.time)

    calendar.add(Calendar.DAY_OF_MONTH, 1)
    val tomorrow = calendar.get(Calendar.DAY_OF_MONTH)
    val tomorrowMonth = SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.time)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start=47.dp,end=47.dp,top=60.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(24.dp)
                    .offset(x= (-20).dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF706D54)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Pickup\nTime",
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.lexend_bold)),
            fontWeight = FontWeight.Bold,
            color = Color(0xFF706D54),
            lineHeight = 40.sp
        )

        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(122.dp)
                    .height(113.dp)
                    .border(
                        width = if (selectedDay == "Today") 2.dp else 1.dp,
                        color = if (selectedDay == "Today") Color(0xFFA08963) else Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .shadow(
                        elevation = if (selectedDay == "Today") 4.dp else 0.dp,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(
                        color = if (selectedDay == "Today") Color(0xFFD9D9D9) else Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { selectedDay = "Today" },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Today",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        color = Color(0xFFA08963)
                    )
                    Text(
                        text = today.toString(),
                        fontSize = 40.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF706D54)
                    )
                    Text(
                        text = todayMonth,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF706D54)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .offset(x=38.dp)
                    .width(122.dp)
                    .height(113.dp)
                    .border(
                        width = if (selectedDay == "Tomorrow") 2.dp else 1.dp,
                        color = if (selectedDay == "Tomorrow") Color(0xFFA08963) else Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(
                        color = if (selectedDay == "Tomorrow") Color(0xFFD9D9D9) else Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { selectedDay = "Tomorrow" },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tomorrow",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        color = Color(0xFFA08963)
                    )
                    Text(
                        text = tomorrow.toString(),
                        fontSize = 40.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF706D54)
                    )
                    Text(
                        text = tomorrowMonth,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF706D54)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Now option (only for today)
            if (selectedDay == "Today") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom=10.dp)
                        .height(48.dp)
                        .border(
                            width = if (selectedTime == "Now") 2.dp else 1.dp,
                            color = if (selectedTime == "Now") Color(0xFFA08963) else Color(0xFF706D54),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { selectedTime = "Now" },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "Now",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = if (selectedTime == "Now") Color(0xFFA08963) else Color(0xFF706D54),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
            timeSlots.forEach { timeSlot ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom=10.dp)
                        .height(48.dp)
                        .border(
                            width = if (selectedTime == timeSlot) 2.dp else 1.dp,
                            color = if (selectedTime == timeSlot) Color(0xFFA08963) else Color(0xFF706D54),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { selectedTime = timeSlot },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = timeSlot,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = if (selectedTime == timeSlot) Color(0xFFA08963) else Color(0xFF706D54),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (selectedDay.isNotEmpty() && selectedTime.isNotEmpty()) {
                    onConfirm(selectedDay, selectedTime)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-80).dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedDay.isNotEmpty() && selectedTime.isNotEmpty()) {
                    Color(0xFF706D54)
                } else {
                    Color(0xFFB8B5A1)
                }
            ),
            enabled = selectedDay.isNotEmpty() && selectedTime.isNotEmpty(), // TAMBAH INI
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Confirm",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}