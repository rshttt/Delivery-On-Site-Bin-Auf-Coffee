package com.example.deliveryonsitebinaufcoffee

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.deliveryonsitebinaufcoffee.model.Category
import com.example.deliveryonsitebinaufcoffee.model.Product
import com.example.deliveryonsitebinaufcoffee.utils.ApiConstants

@Composable
fun CategoryCard(
    category: Category,
    isSelected: Boolean = false,
    onCategoryClick: (Category) -> Unit = {}
) {
    val imageUrl = remember(category.id) {
        category.image_path?.let { path ->
            val cleanPath = path.removePrefix("/")
            "http://192.168.111.198:8001/$cleanPath"
        }
    }

    Card(
        modifier = Modifier
            .size(76.dp)
            .clickable { onCategoryClick(category) }, // Tambahkan click handler
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFC9B194) else Color(0xFF8B8768) // Warna berubah saat dipilih
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = category.name,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.lexend_bold))
            )

            val context = LocalContext.current
            val imageRequest = ImageRequest.Builder(context)
                .data(imageUrl)
                .size(60)
                .scale(Scale.FILL)
                .crossfade(true)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .build()

            // AsyncImage sederhana tanpa ImageRequest
            AsyncImage(
                model = imageRequest,
                contentDescription = category.name,
                modifier = Modifier
                    .size(70.dp)
                    .offset(x = 27.dp, y = 15.dp),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_launcher_foreground),
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                onSuccess = {
                    println("✅ ${category.name} loaded successfully")
                },
                onError = { error ->
                    println("❌ ${category.name} failed: ${error.result.throwable?.message}")
                    println("URL: $imageUrl")
                }
            )
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    isFavorite: Boolean, // Tambahkan parameter terpisah untuk favorite state
    onClickFav: () -> Unit,
    onProductClick: () -> Unit,
    categories: List<com.example.deliveryonsitebinaufcoffee.model.Category> = emptyList(),
) {
    val imageUrl = remember(product.id) {
        product.image_path?.let { path ->
            val cleanPath = path.removePrefix("/")
            "http://192.168.111.198:8001/$cleanPath"
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(12.dp)
            )
            .height(188.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
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
                        val context = LocalContext.current
                        val imageRequest = ImageRequest.Builder(context)
                            .data(imageUrl)
                            .size(60)
                            .scale(Scale.FILL)
                            .crossfade(true)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground)
                            .build()

                        // Gunakan AsyncImage untuk load image dari URL
                        AsyncImage(
                            model = imageRequest,
                            contentDescription = product.name,
                            modifier = Modifier
                                .padding(4.dp),
                            contentScale = ContentScale.FillHeight,
                            // Fallback jika image gagal load
                            error = painterResource(id = R.drawable.ic_launcher_foreground),
                            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                            onSuccess = {
                                println("✅ ${product.name} loaded successfully")
                            },
                            onError = { error ->
                                println("❌ ${product.name} failed: ${error.result.throwable?.message}")
                                println("URL: $imageUrl")
                            }
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
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.outline,
                            maxLines = 2,
                            lineHeight = 16.sp
                        )
                    }

                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray,
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
                        text = categories.find { it.id == product.categoryId }?.name ?: "Unknown", // Gunakan field category dari Product
                        fontSize = 8.sp,
                        lineHeight = 14.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = MaterialTheme.colorScheme.outline,
                    )

                    Text(
                        text = "Rp${String.format("%,d", product.price).replace(",", ".")}", // Format harga dengan titik
                        fontSize = 11.sp,
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

