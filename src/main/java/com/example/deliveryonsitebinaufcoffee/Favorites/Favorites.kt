package com.example.deliveryonsitebinaufcoffee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.deliveryonsitebinaufcoffee.model.Product

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    productItems: List<Product>,
    categories: List<com.example.deliveryonsitebinaufcoffee.model.Category> = emptyList(), // Add categories parameter
    onClickFav: (Int) -> Unit, // Pass product ID instead of index
    onBackClick: (() -> Unit)? = null,
    onProductClick: (Product) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
            .background(Color.White)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y=-15.dp)
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
        )  {
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
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFFA08963),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.size(32.dp))
                    }

                    Text(
                        text = "FAVORITES",
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

        Spacer(modifier = Modifier.height(0.dp))

        if (productItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "No favorites",
                        tint = Color(0xFFCCCCCC),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No favorites yet",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = Color(0xFF999999),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Add some products to your favorites!",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = Color(0xFFCCCCCC),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(21.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 41.dp),
                userScrollEnabled = true,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 0.dp)
            ) {
                items(productItems) { product ->
                    FavoriteProductCard(
                        product = product,
                        categories = categories, // Pass categories
                        onClickFav = { onClickFav(product.id) }, // Pass product ID
                        onProductClick = {
                            onProductClick(product)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteProductCard(
    product: Product, // Use the correct Product type from model
    categories: List<com.example.deliveryonsitebinaufcoffee.model.Category> = emptyList(), // Add categories parameter
    onClickFav: () -> Unit,
    onProductClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(16.dp)
            )
            .height(188.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Use AsyncImage for loading image from URL like in ProductCard
                        AsyncImage(
                            model = product.image_path,
                            contentDescription = product.name,
                            modifier = Modifier
                                .padding(4.dp),
                            contentScale = ContentScale.Fit,
                            // Fallback if image fails to load
                            error = painterResource(id = R.drawable.ic_launcher_foreground)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .size(width = 100.dp, height = 32.dp)
                            .padding(start = 4.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = product.name,
                            fontFamily = FontFamily(Font(R.font.lexend_bold)),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.outline,
                            maxLines = 2,
                            lineHeight = 16.sp
                        )
                    }

                    // Since this is favorites screen, all items are favorites by default
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                onClickFav()
                            }
                    )
                }
            }

            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = categories.find { it.id == product.categoryId }?.name ?: "Unknown", // Look up category name by ID
                        fontSize = 8.sp,
                        lineHeight = 14.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = MaterialTheme.colorScheme.outline,
                    )

                    Text(
                        text = "Rp${String.format("%,d", product.price).replace(",", ".")}", // Format price with dots
                        fontSize = 10.sp,
                        lineHeight = 8.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Button(
                    onClick = { onProductClick() },
                    modifier = Modifier
                        .size(width = 40.dp, height = 36.dp)
                        .align(alignment = Alignment.Bottom),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                    shape = RectangleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }
        }
    }
}