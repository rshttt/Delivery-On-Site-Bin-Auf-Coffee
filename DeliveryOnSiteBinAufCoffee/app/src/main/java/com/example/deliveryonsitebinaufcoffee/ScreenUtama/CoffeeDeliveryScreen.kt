package com.example.deliveryonsitebinaufcoffee
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deliveryonsitebinaufcoffee.model.Category
import com.example.deliveryonsitebinaufcoffee.model.Product
import com.example.deliveryonsitebinaufcoffee.viewmodel.AccountItems
import com.example.deliveryonsitebinaufcoffee.viewmodel.ProductViewModel

@Composable
fun CoffeeDeliveryScreen(
    modifier: Modifier = Modifier,
    inputSearch: String,
    categories: List<Category>,
    productItems: List<Product>,
    account: AccountItems.PublicAccount?,
    onInputSearch: (String) -> Unit,
    onClickFav: (Int) -> Unit,
    onProductClick: (Product) -> Unit,
    viewModel: ProductViewModel,
) {

    val categoriesFromViewModel by viewModel.categories.collectAsState()

    // State untuk kategori yang dipilih
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }

    // Filter products berdasarkan search input dan kategori
    val filteredProducts = remember(inputSearch, productItems, selectedCategoryId) {
        var filtered = productItems

        // Filter berdasarkan kategori terlebih dahulu
        if (selectedCategoryId != null) {
            filtered = filtered.filter { product ->
                product.categoryId == selectedCategoryId
            }
        }

        // Kemudian filter berdasarkan search input
        if (inputSearch.isBlank()) {
            filtered
        } else {
            filtered.filter { product ->
                product.name.contains(inputSearch, ignoreCase = true) ||
                        product.description?.contains(inputSearch, ignoreCase = true) == true
            }
        }
    }

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
                            text = account?.name ?: "Guest",
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
                    text = "Mau ngopi nih?\nYuk, mulai dengan pilih menu favoritmu!",
                    fontSize = 14.sp,
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

            // Search field
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

            // Categories title dengan tombol "All" untuk reset filter
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Categories",
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.outline,
                    )

                    // Tombol "All" untuk reset filter
                    Text(
                        text = "All",
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontSize = 14.sp,
                        color = if (selectedCategoryId == null) Color(0xFF706D54) else MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .clickable {
                                selectedCategoryId = null
                            }
                            .padding(8.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Log.d("CategoryDebug", "Categories from ViewModel: $categoriesFromViewModel")
                LazyRow(
                    contentPadding = PaddingValues(start = 40.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(categoriesFromViewModel) { category ->
                        CategoryCard(
                            category = category,
                            isSelected = selectedCategoryId == category.id,
                            onCategoryClick = { clickedCategory ->
                                selectedCategoryId = if (selectedCategoryId == clickedCategory.id) {
                                    null // Unselect jika kategori yang sama diklik lagi
                                } else {
                                    clickedCategory.id // Select kategori baru
                                }
                            }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Title yang berubah berdasarkan filter
            item {
                val titleText = when {
                    selectedCategoryId != null -> {
                        val selectedCategory = categoriesFromViewModel.find { it.id == selectedCategoryId }
                        selectedCategory?.name ?: "Filtered Products"
                    }
                    inputSearch.isNotBlank() -> "Search Results"
                    else -> "All Item"
                }

                Text(
                    text = titleText,
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
            if (filteredProducts.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val message = when {
                            inputSearch.isNotBlank() && selectedCategoryId != null -> {
                                val categoryName = categoriesFromViewModel.find { it.id == selectedCategoryId }?.name ?: "Selected Category"
                                "No products found for \"$inputSearch\" in $categoryName"
                            }
                            inputSearch.isNotBlank() -> "No products found for \"$inputSearch\""
                            selectedCategoryId != null -> {
                                val categoryName = categoriesFromViewModel.find { it.id == selectedCategoryId }?.name ?: "Selected Category"
                                "No products found in $categoryName"
                            }
                            else -> "No products available"
                        }

                        Text(
                            text = message,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.lexend_light)),
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            } else {
                // Tampilkan products dalam grid 2 kolom
                items(filteredProducts.chunked(2)) { productPair ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        productPair.forEach { product ->
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                ProductCard(
                                    product = product,
                                    isFavorite = product.isFavorite,
                                    onClickFav = {
                                        onClickFav(product.id)
                                    },
                                    onProductClick = {
                                        onProductClick(product)
                                    },
                                    categories = categoriesFromViewModel
                                )
                            }
                        }

                        // Jika hanya ada 1 produk di baris terakhir, tambahkan spacer
                        if (productPair.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                    // Tambahkan spacing antar baris, kecuali baris terakhir
                    if (productPair != filteredProducts.chunked(2).last()) {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}