package com.example.deliveryonsitebinaufcoffee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import coil.compose.AsyncImage
import com.example.deliveryonsitebinaufcoffee.model.Product

@Composable
fun ProductDetailScreen(
    product: Product,
    onBack: () -> Unit,
    onAddToOrder: (Product, Int, String, String, String) -> Unit,

) {
    var quantity by remember { mutableIntStateOf(1) }
    var selectedSize by remember { mutableStateOf("") }
    var selectedSugar by remember { mutableStateOf("") }
    var selectedIce by remember { mutableStateOf("") }
    var showValidationMessage by remember { mutableStateOf(false) }

    val isAllFieldsSelected = selectedSize.isNotEmpty() && selectedSugar.isNotEmpty() && selectedIce.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 28.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF706D54)
                )
            }

            Text(
                text = "DETAILS",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                fontWeight = FontWeight(700),
                color = Color(0xFFA08963),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),

                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(214.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFD9D9D9)),
                        contentAlignment = Alignment.Center
                    ) {
                            AsyncImage(
                                model = product.image_path, // Fixed: Use product instead of cartItem
                                contentDescription = product.name,
                            modifier = Modifier
                                .size(200.dp)
                                .offset(x = 0.dp, y = 20.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp,end = 20.dp)
                        .padding(8.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE0E0E0),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = product.name,
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                                color = Color(0xFF706D54)
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(
                                        Color(0xFFF5F5F5),
                                        RoundedCornerShape(16.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = "Rating",
                                    tint = Color(0xFFFFB800),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "4.8",
                                    fontSize = 12.sp,
                                    color = Color(0xFF706D54),
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = product.description,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.lexend_light)),
                                color = Color(0xFF706D54),
                                lineHeight = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Rp${product.price}",
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.lexend_bold)),
                            color = Color(0xFFA08963)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.padding(start = 35.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Size Options",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = Color(0xFF706D54)
                    )
                    Text(
                        text = " *",
                        fontSize = 14.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 20.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(47.dp)
                ) {
                    listOf("Small", "Medium", "Large").forEach { size ->
                        OptionButton(
                            text = size,
                            isSelected = selectedSize == size,
                            onClick = {
                                selectedSize = size
                                showValidationMessage = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Sugar Level Section
                Row(
                    modifier = Modifier.padding(start = 35.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Level Sugar",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = Color(0xFF706D54)
                    )
                    Text(
                        text = " *",
                        fontSize = 14.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 20.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(47.dp)
                ) {
                    listOf("Less", "Normal", "Extra").forEach { sugar ->
                        OptionButton(
                            text = sugar,
                            isSelected = selectedSugar == sugar,
                            onClick = {
                                selectedSugar = sugar
                                showValidationMessage = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Ice Level Section
                Row(
                    modifier = Modifier.padding(start = 35.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Level Ice",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = Color(0xFF706D54)
                    )
                    Text(
                        text = " *",
                        fontSize = 14.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 20.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(47.dp)
                ) {
                    listOf("Less", "Normal", "Extra").forEach { ice ->
                        OptionButton(
                            text = ice,
                            isSelected = selectedIce == ice,
                            onClick = {
                                selectedIce = ice
                                showValidationMessage = false
                            }
                        )
                    }
                }

                if (showValidationMessage) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Please select size, sugar level, and ice level before adding to order",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = Color.Red,
                        modifier = Modifier.padding(horizontal = 35.dp),
                        lineHeight = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(118.dp)
                .background(
                    Color(0xFFC9B194),
                    RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)
                )
                .padding(horizontal = 30.dp, vertical = 8.dp)
                .padding(bottom = 40.dp)
                .imePadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .offset(x=4.dp,y=10.dp)
                    .background(
                        Color.Transparent,
                        RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(31.dp)
                        .shadow(
                            elevation = 6.dp,
                            shape = CircleShape
                        )
                        .background(
                            Color.White,
                            CircleShape
                        )
                        .clickable { if (quantity > 1) quantity-- },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .offset(y= (-7).dp),
                        text = "-",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontFamily = FontFamily(Font(R.font.lexend_light)),
                            fontWeight = FontWeight(1000),
                            color = Color(0xFFA08963),

                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Text(
                    text = quantity.toString(),
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .size(31.dp)
                        .shadow(
                            elevation = 6.dp,
                            shape = CircleShape
                        )
                        .background(
                            Color.White,
                            CircleShape
                        )
                        .clickable { quantity++ },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .offset(y=-7.dp),
                        text = "+",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontFamily = FontFamily(Font(R.font.lexend_light)),
                            fontWeight = FontWeight(1000),
                            color = Color(0xFFA08963),

                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
            Button(
                onClick = {
                    if (isAllFieldsSelected) {
                        onAddToOrder(product, quantity, selectedSize, selectedSugar, selectedIce)
                    } else {
                        showValidationMessage = true
                    }
                },
                modifier = Modifier
                    .offset(x=10.dp,y=10.dp)
                    .width(170.dp)
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isAllFieldsSelected) Color(0xFF706D54) else Color(0xFF706D54).copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Add to order",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    color = Color.White
                )
            }
        }
    }
}