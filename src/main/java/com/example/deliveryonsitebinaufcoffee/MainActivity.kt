package com.example.deliveryonsitebinaufcoffee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        })
    }
}

@Composable
fun MainScreen(data: ProductItems = viewModel()) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var inputSearch by rememberSaveable { mutableStateOf("") }
    val categories by data.categories.collectAsState()
    val product by data.products.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars),
        bottomBar = {
            BottomNavigationBar(selectedTab = selectedTab) { index ->
                selectedTab = index
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> CoffeeDeliveryScreen(
                modifier = Modifier.padding(innerPadding),
                inputSearch = inputSearch,
                categories = categories,
                productItems = product,
                onInputSearch = { newInput ->
                    inputSearch = newInput
                },
                onClickFav = { index ->
                    data.toggleFavorite(index)
                }
            )
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Coming soon...")
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        containerColor = MaterialTheme.colorScheme.outline
    ) {
        NavItems().icons.mapIndexed { index, item ->
            NavigationBarItem (
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                icon = {
                    val color = if(selectedTab == index) Color.White else Color(0xFFDBDBDB)
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = color
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun CoffeeDeliveryScreen(
    modifier: Modifier = Modifier,
    inputSearch: String,
    categories: List<Category.CategoryItems>,
    productItems: List<ProductItems.Product>,
    onInputSearch: (String) -> Unit,
    onClickFav: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(bottom = 56.dp)
    ) {
        LazyColumn {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp)
                        .padding(top = 44.dp, bottom = 40.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .align(alignment = Alignment.CenterVertically)
                    ) {
                        Text(
                            text = "Good Morning,",
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.lexend_bold)),
                            color = Color(0xFFA08963),
                        )

                        Text(
                            text = "Aliss!",
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.lemon_reg)),
                            color = Color(0xFF706D54),
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.alis),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(62.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            item {
                Text(
                    text = "Blabla blibli blublu\nbleble, Lis?",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.tertiary,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_light)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                OutlinedTextField(
                    value = inputSearch,
                    onValueChange = onInputSearch,
                    placeholder = {
                        Text(
                            text = "Search",
                            fontFamily = FontFamily(Font(R.font.lexend_light)),
                            color = MaterialTheme.colorScheme.outline
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.outline
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    shape = RoundedCornerShape(48.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedContainerColor = Color(0xFFE8E8E8),
                        focusedContainerColor = Color(0xFFE8E8E8)
                    ),
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = MaterialTheme.colorScheme.outline
                    )
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Text(
                    text = "Categories",
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(start = 40.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(categories) { category ->
                        CategoryCard(category = category)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Text(
                    text = "Best Seller",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier
                        .height(400.dp)
                        .padding(horizontal = 40.dp)
                ) {
                    itemsIndexed(productItems) { index, product ->
                        ProductCard(product = product) {
                            onClickFav(index)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Text(
                    text = "All Products",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                LazyColumn(
                    modifier = Modifier
                        .height(300.dp)
                        .padding(horizontal = 40.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productItems) { product ->
                        ProductRowCard(product = product)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun CategoryCard(category: Category.CategoryItems) {
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
                text = category.categoryName,
                fontSize = 14.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                fontWeight = FontWeight(700),
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
            Image(
                painter = painterResource(id = category.image),
                contentDescription = category.categoryName,
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
fun ProductCard(
    product: ProductItems.Product,
    onClickFav: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.Gray.copy(alpha = 0.3f),
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
                        Image(
                            painter = painterResource(id = product.image),
                            contentDescription = product.name,
                            modifier = Modifier
                                .padding(4.dp),
                            contentScale = ContentScale.Fit
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
                    Text(
                        text = product.name,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.outline,
                        maxLines = 2,
                        lineHeight = 16.sp,
                        modifier = Modifier
                            .width(100.dp)
                            .padding(start = 4.dp)
                    )

                    Icon(
                        imageVector = if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (product.isFavorite) Color.Red else Color.Gray,
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
                        text = "Drink",
                        fontSize = 8.sp,
                        lineHeight = 14.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = MaterialTheme.colorScheme.outline,
                    )

                    Text(
                        text = "Rp. ${product.price}",
                        fontSize = 10.sp,
                        lineHeight = 8.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Button(
                    onClick = {},
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

@Composable
fun ProductRowCard(product: ProductItems.Product) {
    var isFavorite by remember { mutableStateOf(product.isFavorite) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(
                width = 2.dp,
                color = Color.Gray.copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
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
                        text = "Rp. ${product.price}",
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
