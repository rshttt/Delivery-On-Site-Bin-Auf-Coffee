package com.example.deliveryonsitebinaufcoffee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Order(
    val id: String,
    val date: String,
    val itemCount: Int,
    val price: String,
    val status: String,
    val imageRes: Int
)

@Composable
fun Orderscreen(
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    onRateClick: ((Order) -> Unit)? = null
) {
    var selected by remember { mutableStateOf("Past") }

    val pastOrders = listOf(
        Order("1", "Sun, 25th May 2025", 2, "Rp32.000", "Delivered", R.drawable.minum),
        Order("2", "Sun, 25th May 2025", 2, "Rp32.000", "Delivered", R.drawable.cake),
        Order("3", "Sun, 25th May 2025", 2, "Rp32.000", "Delivered", R.drawable.coffee5)
    )

    val upcomingOrders = listOf(
        Order("4", "Mon, 26th May 2025", 1, "Rp25.000", "Preparing", R.drawable.non6),
        Order("5", "Tue, 27th May 2025", 3, "Rp45.000", "Confirmed", R.drawable.non4),
        Order("6", "Mon, 26th May 2025", 1, "Rp25.000", "Preparing", R.drawable.non6),
        Order("7", "Tue, 27th May 2025", 3, "Rp45.000", "Confirmed", R.drawable.non4)
    )

    val orders = if (selected == "Past") pastOrders else upcomingOrders

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-15).dp)
                    .shadow(
                        elevation = 90.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000),
                        shape = RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp)
                    )
                    .border(
                        width = 8.dp,
                        color = Color(0x40000000),
                        shape = RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp)
                    )
                    .padding(9.dp)
                    .height(120.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (onBackClick != null) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable { onBackClick() },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.back),
                                    contentDescription = "Back Arrow",
                                    modifier = Modifier.size(28.dp),
                                    contentScale = ContentScale.Inside
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.size(32.dp))
                        }

                        Text(
                            text = "MY ORDER",
                            fontSize = 32.sp,
                            fontFamily = FontFamily(Font(R.font.lexend_bold)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFFA08963),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.size(32.dp))
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.size(18.dp))
        }
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(291.dp)
                        .height(42.dp)
                        .clip(RoundedCornerShape(41))
                        .background(Color(0xFFFFFFFF))
                        .border(
                            width = 4.dp,
                            color = Color(0xFFC9B194),
                            shape = RoundedCornerShape(41)
                        )
                ) {
                    Row(
                        Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(41))
                                .background(if (selected == "Past") Color(0xFFC9B194) else Color.Transparent)
                                .clickable { selected = "Past" },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Past",
                                color = if (selected == "Past") Color.White else Color(0xFFA08963),
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                                fontWeight = FontWeight(700),
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(41))
                                .background(if (selected == "Upcoming") Color(0xFFC9B194) else Color.Transparent)
                                .clickable { selected = "Upcoming" },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Upcoming",
                                color = if (selected == "Upcoming") Color.White else Color(0xFFA08963),
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                                fontWeight = FontWeight(700),
                            )
                        }
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        items(orders) { order ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp, vertical = 15.dp),
                contentAlignment = Alignment.Center
            ) {
                OrderCard(
                    order = order,
                    isPast = selected == "Past",
                    onRateClick = onRateClick
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun OrderCard(
    order: Order,
    isPast: Boolean,
    modifier: Modifier = Modifier,
    onRateClick: ((Order) -> Unit)? = null
) {
    Card(
        modifier = modifier
            .width(295.dp)
            .height(172.dp)
            .border(
                width = 4.dp,
                color = Color(0x40000000),
                shape = RoundedCornerShape(18.dp),
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start =7.dp, end = 7.dp, top = 20.dp, bottom = 23.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = order.imageRes),
                    contentDescription = "Coffee Image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy((-5).dp)
                ) {
                    Text(
                        text = order.date,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFFA08963)
                    )

                    Text(
                        text = "${order.itemCount} items",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFA08963)
                    )

                    Text(
                        text = order.price,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFFA08963)
                    )

                    Text(
                        text = order.status,
                        fontSize = 11.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFA08963)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    onClick = {
                        if (isPast && onRateClick != null) {
                            onRateClick(order)
                        }
                    },
                    modifier = Modifier
                        .width(123.dp)
                        .height(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF706D54)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (isPast) "Rate" else "Track",
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontWeight = FontWeight(500),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = { },
                    modifier = Modifier
                        .width(123.dp)
                        .height(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF706D54)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (isPast) "Re-Order" else "Cancel",
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontWeight = FontWeight(500),
                        color = Color.White
                    )
                }
            }
        }
    }
}