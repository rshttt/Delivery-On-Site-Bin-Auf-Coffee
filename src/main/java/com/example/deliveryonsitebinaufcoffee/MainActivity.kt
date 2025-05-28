package com.example.deliveryonsitebinaufcoffee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliveryonsitebinaufcoffee.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
}

data class Category(val name: String, val icon: Int)
data class Product(val name: String, val price: String, val image: Int, var isFavorite: Boolean = false)

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(selectedTab = selectedTab) { index ->
                selectedTab = index
            }
        }
    ) { innerPadding ->
        CoffeeDeliveryScreen(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .width(390.dp)
            .height(100.dp),
        containerColor = Color(0xFF706D54)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (selectedTab == 0) Color.White else Color.White.copy(alpha = 0.6f)
                )
            },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Favorites",
                    tint = if (selectedTab == 1) Color.White else Color.White.copy(alpha = 0.6f)
                )
            },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = if (selectedTab == 2) Color.White else Color.White.copy(alpha = 0.6f)
                )
            },
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = if (selectedTab == 3) Color.White else Color.White.copy(alpha = 0.6f)
                )
            },
            selected = selectedTab == 3,
            onClick = { onTabSelected(3) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color.Transparent
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeDeliveryScreen(modifier: Modifier = Modifier) {
    val categories = listOf(
        Category("Drink", R.drawable.minum),
        Category("Cake", R.drawable.cake),
        Category("Snack", R.drawable.kentang),
        Category("Meal", R.drawable.meals)
    )

    val bestSellers = listOf(
        Product("Chocolate yummy", "Rp25000", R.drawable.minum),
        Product("Strawberry doughnut", "Rp10000", R.drawable.cake),
        Product("Strawberry doughnut", "Rp10000", R.drawable.kentang),
        Product("Chocolate yummy", "Rp25000", R.drawable.meals)
    )
    val allProducts = listOf(
        Product("Espresso Classic", "Rp15000", R.drawable.minum),
        Product("Sego Goreng", "Rp18000", R.drawable.meals),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Good Morning,",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFFA08963),
                    )

                    Text(
                        text = "Aliss!",
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.lemon_reg)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF706D54),
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    painter = painterResource(id = R.drawable.alis),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(62.dp)
                        .clip(RoundedCornerShape(22.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Blabla blibli blublu\nbleble, Lis?",
                fontSize = 14.sp,
                color = Color(0xFF8B7355),
                lineHeight = 20.sp,
                fontFamily = FontFamily(Font(R.font.lexend_light)),
            )}

        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = { },
                placeholder = {
                    Text(
                        text = "Search",
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = Color(0xFF8B7355)
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF8B7355)
                    )
                },
                modifier = Modifier
                    .width(350.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(47.dp)),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = Color(0xFFE8E8E8),
                    focusedContainerColor = Color(0xFFE8E8E8)
                )
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ){
            Text(
                text = "Categories",
                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF706D54)
            )}

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(categories) { category ->
                CategoryCard(category = category)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ){
            Text(
                text = "Best Seller",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                fontWeight = FontWeight(700),
                color = Color(0xFF706D54)
            )}

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(400.dp)
        ) {
            items(bestSellers) { product ->
                ProductCard(product = product)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ){
            Text(
                text = "All Products",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                fontWeight = FontWeight(700),
                color = Color(0xFF706D54)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(
            modifier = Modifier.height(300.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(allProducts) { product ->
                ProductRowCard(product = product)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun CategoryCard(category: Category) {
    Card(
        modifier = Modifier
            .size(76.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF706D54)
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
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                fontWeight = FontWeight(700),
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
            Image(
                painter = painterResource(id = category.icon),
                contentDescription = category.name,
                modifier = Modifier
                    .size(60.dp)
                    .offset(x = 27.dp, y = 15.dp)
                    .align(Alignment.BottomEnd),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    var isFavorite by remember { mutableStateOf(product.isFavorite) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .padding(horizontal = 10.dp)
            .padding(vertical = 7.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFFFFF)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = product.image),
                            contentDescription = product.name,
                            modifier = Modifier.size(100.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                Spacer(modifier = Modifier.height(3.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 60.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Text(
                        text = product.name,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF5D4E37),
                        maxLines = 2,
                        lineHeight = 15.sp
                    )
                    Text(
                        text = "Drink",
                        fontSize = 8.sp,
                        lineHeight = 15.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        fontWeight = FontWeight(300),
                        color = Color(0xFF8B7355),
                    )
                    Text(
                        text = product.price,
                        fontSize = 11.sp,
                        lineHeight = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5D4E37),
                        modifier = Modifier
                            .padding(bottom = 2.dp, top = 1.dp)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(-15.dp),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 8.dp)
                    .background(Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { isFavorite = !isFavorite },
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(1.dp)
                        .offset(x = 7.dp, y = 20.dp)
                        .background(
                            color = Color(0xFFA08963),
                            shape = RoundedCornerShape(0.dp)
                        )
                        .clickable { },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier
                            .offset(x = -2.dp, y = -5.dp),
                        text = "+",
                            fontSize = 28.sp,
                            fontFamily = FontFamily(Font(R.font.lexend_light)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )

                }
            }
        }
    }
}

@Composable
fun ProductRowCard(product: Product) {
    var isFavorite by remember { mutableStateOf(product.isFavorite) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF0F0F0)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = product.image),
                        contentDescription = product.name,
                        modifier = Modifier.size(60.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 60.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = product.name,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF5D4E37),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                    )
                    Text(
                        text = "drink",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF8B7355),
                        fontFamily = FontFamily(Font(R.font.lexend_light))
                    )
                    Text(
                        text = product.price,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5D4E37)
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { isFavorite = !isFavorite },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(
                            color = Color(0xFFA08963),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add to cart",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoffeeDeliveryScreenPreview() {
    AppTheme {
        MainScreen()
    }
}
