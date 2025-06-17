package com.example.deliveryonsitebinaufcoffee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class RatingItem(
    val name: String,
    val price: String,
    val imageRes: Int,
    var rating: Int = 0,
    var review: String = ""
)

@Composable
fun RateAndReviewScreen(
    order: Order,
    onBackClick: () -> Unit,
    onSendRating: (List<RatingItem>) -> Unit
) {
    val ratingItems = remember {
        mutableStateListOf(
            RatingItem("Ice Yummy umm", "Rp22.000", order.imageRes)
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF706D54),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    modifier = Modifier.padding(top = 18.dp, start = 40.dp),
                    text = "RATE &\nREVIEW",
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF706D54),
                    textAlign = TextAlign.Start,
                    lineHeight = 40.sp
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(25.dp))
        }

        itemsIndexed(ratingItems) { index, item ->
            RatingItemCard(
                item = item,
                isSelected = index == 0,
                onRatingChanged = { newRating ->
                    ratingItems[index] = item.copy(rating = newRating)
                },
                onReviewChanged = { newReview ->
                    ratingItems[index] = item.copy(review = newReview)
                }
            )

            if (index < ratingItems.size - 1) {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
        }

        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { onSendRating(ratingItems) },
                    modifier = Modifier
                        .width(204.dp)
                        .height(45.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFA08963)
                    ),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text(
                        text = "SEND",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontWeight = FontWeight(700),
                        color = Color.White
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun RatingItemCard(
    item: RatingItem,
    isSelected: Boolean,
    onRatingChanged: (Int) -> Unit,
    onReviewChanged: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .border(
                width = if (isSelected) 6.dp else 1.dp,
                color = if (isSelected) Color(0xFFDBDBDB) else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    modifier = Modifier
                        .width(73.dp)
                        .height(91.dp)
                        .padding(start = 20.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        modifier = Modifier.padding(top = 14.dp),
                        text = item.name,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFFA08963)
                    )
                    Text(
                        text = item.price,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = Color(0xFFA08963)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(5) { index ->
                            Icon(
                                imageVector = if (index < item.rating) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "Star ${index + 1}",
                                tint = if (index < item.rating) Color(0xFFFFB800) else Color(0xFFE0E0E0),
                                modifier = Modifier
                                    .size(33.dp)
                                    .clickable {
                                        onRatingChanged(index + 1)
                                    }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(start = 25.dp),
                text = "Share reviews",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.lexend_light)),
                fontWeight = FontWeight(1000),
                color = Color(0xFFA08963),
            )

            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 22.dp,end=22.dp, bottom = 15.dp)
                    .height(80.dp)
                    .border(1.dp, Color(0xFF706D54), RoundedCornerShape(11.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF8F8F8)
                ),
                shape = RoundedCornerShape(11.dp)
            ) {
                BasicTextField(
                    value = item.review,
                    onValueChange = onReviewChanged,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    textStyle = TextStyle(
                        fontSize = 11.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = Color(0xFF706D54)
                    ),
                    decorationBox = { innerTextField ->
                        if (item.review.isEmpty()) {
                            Text(
                                text = "Blablabla",
                                fontSize = 11.sp,
                                fontFamily = FontFamily(Font(R.font.lexend_light)),
                                color = Color(0xFFB0B0B0)
                            )
                        }
                        innerTextField()
                    }
                )
            }
        }
    }
}
