package com.example.deliveryonsitebinaufcoffee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deliveryonsitebinaufcoffee.Profile.RateAndReviewScreen
import com.example.deliveryonsitebinaufcoffee.ui.theme.AppTheme
import com.example.deliveryonsitebinaufcoffee.model.Product
import com.example.deliveryonsitebinaufcoffee.viewmodel.AccountItems
import com.example.deliveryonsitebinaufcoffee.viewmodel.ProductViewModel

data class CartItem(
    val product: Product,
    val quantity: Int,
    val size: String,
    val sugar: String,
    val ice: String
) {
    val totalPrice: Int
        get() = product.price * quantity
}
fun debugCartItems(cartItems: List<CartItem>) {
    println("Cart Items Count: ${cartItems.size}")
    cartItems.forEach { item ->
        println("Item: ${item.product.name}, Qty: ${item.quantity}, Price: ${item.totalPrice}")
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if user is logged in
        val sharedPreferences = getSharedPreferences("auth_prefs", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)

        if (token == null) {
            // User belum login, redirect ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        enableEdgeToEdge()
        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
}
@Composable
fun MainScreen(
    productViewModel: ProductViewModel = viewModel(),
    account: AccountItems = viewModel()
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var inputSearch by rememberSaveable { mutableStateOf("") }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var showDetailScreen by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var cartItems by remember { mutableStateOf<List<CartItem>>(emptyList()) }
    var showCartPopup by remember { mutableStateOf(false) }
    var showConfirmOrderScreen by remember { mutableStateOf(false) }
    var showPickupTimeScreen by remember { mutableStateOf(false) }
    var editingCartItem by remember { mutableStateOf<CartItem?>(null) }
    var isLoggingOut by remember { mutableStateOf(false) }

    // Collect state dari ViewModel
    val categories by productViewModel.categories.collectAsState()
    val productUiStates by productViewModel.products.collectAsState()
    val isLoading by productViewModel.isLoading.collectAsState()
    val errorMessage by productViewModel.errorMessage.collectAsState()
    val userAccount by account.userAccount.collectAsState()

    // Get auth token from SharedPreferences
    val sharedPreferences = remember {
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    }

    // Get the raw token and add Bearer prefix if needed
    val authToken = remember {
        val rawToken = sharedPreferences.getString("token", "") ?: ""
        if (rawToken.isNotEmpty() && !rawToken.startsWith("Bearer ")) {
            "Bearer $rawToken"
        } else if (rawToken.startsWith("Bearer ")) {
            rawToken
        } else {
            ""
        }
    }
    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // Check if user is logged in
            val sharedPreferences = getSharedPreferences("auth_prefs", MODE_PRIVATE)
            val token = sharedPreferences.getString("token", null)

            if (token == null) {
                // User belum login, redirect ke LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return
            }

            enableEdgeToEdge()
            setContent {
                AppTheme {
                    MainScreen()
                }
            }
        }
    }

    // Initialize ProductViewModel with token
    LaunchedEffect(authToken) {
        if (authToken.isNotEmpty()) {
            productViewModel.initializeWithToken(authToken)
        }
    }


    // Also initialize AccountItems with token if needed

    // Konversi ProductUiState ke Product untuk kompatibilitas
    val products = productUiStates.map { it.product }
    val favoriteProducts = productUiStates.filter { it.isFavorite }.map { it.product }

    val totalItems = cartItems.sumOf { it.quantity }
    val totalPrice = cartItems.sumOf { it.totalPrice }

    var selectedPickupDay by remember { mutableStateOf("") }
    var selectedPickupTime by remember { mutableStateOf("") }

    // Loading state
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // Error state
    errorMessage?.let { error ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = error,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        productViewModel.refresh()
                    }
                ) {
                    Text("Retry")
                }
            }
        }
        return
    }

    // Rest of your MainScreen code remains the same...
    if (showPickupTimeScreen) {
        PickupTimeScreen(
            onBackClick = {
                showPickupTimeScreen = false
                showConfirmOrderScreen = true
            },
            onConfirm = { selectedDay, selectedTime ->
                selectedPickupDay = selectedDay
                selectedPickupTime = selectedTime
                println("Selected pickup time: $selectedDay at $selectedTime")
                showPickupTimeScreen = false
                showConfirmOrderScreen = true
            }
        )
    }
    else if (showConfirmOrderScreen) {
        ConfirmOrderScreen(
            cartItems = cartItems,
            onBackClick = {
                showConfirmOrderScreen = false
            },
            onProceedToPay = {
                showConfirmOrderScreen = false
                // Clear cart after successful order
                cartItems = emptyList()
                // Optionally navigate to order history or success screen
            },
            onUpdateQuantity = { cartItem, newQuantity ->
                cartItems = cartItems.map { item ->
                    if (item.product.name == cartItem.product.name &&
                        item.size == cartItem.size &&
                        item.sugar == cartItem.sugar &&
                        item.ice == cartItem.ice
                    ) {
                        item.copy(quantity = newQuantity)
                    } else {
                        item
                    }
                }
                debugCartItems(cartItems)
            },
            onEditItem = { cartItem ->
                editingCartItem = cartItem
                selectedProduct = cartItem.product
                showConfirmOrderScreen = false
                showDetailScreen = true
            },
            onDeleteItem = { cartItem ->
                cartItems = cartItems.filter { item ->
                    !(item.product.name == cartItem.product.name &&
                            item.size == cartItem.size &&
                            item.sugar == cartItem.sugar &&
                            item.ice == cartItem.ice)
                }
                debugCartItems(cartItems)
                if (cartItems.isEmpty()) {
                    showConfirmOrderScreen = false
                }
            },
            onNavigateToPickupTime = {
                showConfirmOrderScreen = false
                showPickupTimeScreen = true
            },
            selectedPickupDay = selectedPickupDay,
            selectedPickupTime = selectedPickupTime,
            userId = userAccount?.id ?: 0,
            authToken = authToken, // This now has Bearer prefix
            orderViewModel = productViewModel
        )
    } else if (showDetailScreen && selectedProduct != null) {
        ProductDetailScreen(
            product = selectedProduct!!,
            onBack = {
                showDetailScreen = false
                selectedProduct = null
                editingCartItem = null
            },
            onAddToOrder = { product, quantity, size, sugar, ice ->
                println("Add to Order called: ${product.name}, qty: $quantity")
                val newCartItem = CartItem(
                    product = product,
                    quantity = quantity,
                    size = size,
                    sugar = sugar,
                    ice = ice
                )
                val existingItemIndex = cartItems.indexOfFirst {
                    it.product.name == product.name &&
                            it.size == size &&
                            it.sugar == sugar &&
                            it.ice == ice
                }

                if (existingItemIndex != -1) {
                    cartItems = cartItems.toMutableList().apply {
                        this[existingItemIndex] = this[existingItemIndex].copy(
                            quantity = this[existingItemIndex].quantity + quantity
                        )
                    }
                } else {
                    cartItems = cartItems + newCartItem
                }
                debugCartItems(cartItems)
                println("Show popup: ${cartItems.isNotEmpty()}")

                showDetailScreen = false
                selectedProduct = null
                showCartPopup = true
            }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(100f)
                    .background(color = Color.White)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                bottomBar = {
                    BottomNavigationBar(selectedTab = selectedTab) { index ->
                        selectedTab = index
                    }
                }
            ) { innerPadding ->
                var showRatingScreen by remember { mutableStateOf(false) }
                var selectedOrderForRating by remember { mutableStateOf<Order?>(null) }

                var showChangePasswordScreen by remember { mutableStateOf(false) }
                var showEditProfileScreen by remember { mutableStateOf(false) }
                var showFeedbackScreen by remember { mutableStateOf(false) }

                when (selectedTab) {
                    0 -> CoffeeDeliveryScreen(
                        modifier = Modifier.padding(innerPadding),
                        inputSearch = inputSearch,
                        categories = categories,  // This is already correct
                        productItems = products,
                        account = userAccount,
                        onInputSearch = { newInput ->
                            inputSearch = newInput
                        },
                        onClickFav = { productId ->
                            productViewModel.toggleFavorite(productId)
                        },
                        onProductClick = { productItem ->
                            selectedProduct = productItem
                            showDetailScreen = true
                        },
                        viewModel = productViewModel  // Add this line to pass the viewModel
                    )
                    1 -> FavoritesScreen(
                        modifier = Modifier.padding(innerPadding),
                        productItems = favoriteProducts,
                        onClickFav = { productId ->
                            // Pass product ID untuk toggle favorite
                            productViewModel.toggleFavorite(productId)
                        },
                        onProductClick = { productItem ->
                            selectedProduct = productItem
                            showDetailScreen = true
                        }
                    )
                    2 -> {
                        if (showRatingScreen && selectedOrderForRating != null) {
                            RateAndReviewScreen(
                                order = selectedOrderForRating!!,
                                onBackClick = {
                                    showRatingScreen = false
                                    selectedOrderForRating = null
                                },
                                onSendRating = { ratings ->
                                    showRatingScreen = false
                                    selectedOrderForRating = null
                                }
                            )
                        } else {
                            Orderscreen(
                                modifier = Modifier.padding(innerPadding),
                                onRateClick = { order ->
                                    selectedOrderForRating = order
                                    showRatingScreen = true
                                }
                            )
                        }
                    }
                    3 -> {
                        when {
                            showFeedbackScreen -> {
                                FeedbackScreen(
                                    onNavigateBack = {
                                        showFeedbackScreen = false
                                    },
                                )
                            }
                            showEditProfileScreen -> {
                                EditProfileScreen(
                                    onNavigateBack = {
                                        showEditProfileScreen = false
                                    }
                                )
                            }
                            showChangePasswordScreen -> {
                                ChangePasswordScreen(
                                    onNavigateBack = {
                                        showChangePasswordScreen = false
                                    }
                                )
                            }
                            else -> {
                                ProfileScreen(
                                    onNavigateToChangePassword = {
                                        showChangePasswordScreen = true
                                    },
                                    onNavigateToEditProfile = {
                                        showEditProfileScreen = true
                                    },
                                    onNavigateToFeedback = {
                                        showFeedbackScreen = true
                                    },
                                    onLogout = {
                                        // Implementasi logout
                                        isLoggingOut = true
                                        account.logoutAccount {
                                            // Navigate ke LoginActivity setelah logout berhasil
                                            val intent = Intent(context, LoginActivity::class.java).apply {
                                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            }
                                            context.startActivity(intent)
                                            (context as? ComponentActivity)?.finish()
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            if (showCartPopup) {
                println("Cart popup should be visible - cartItems: ${cartItems.size}")

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 1.dp)
                        .padding(bottom = 100.dp)
                        .zIndex(100f)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 9.dp, color = Color(0xFFDBDBDB), shape = RoundedCornerShape(16.dp))
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(18.dp)
                            ),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 1.dp, start=16.dp,end=16.dp,bottom=10.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                                    .offset(x=5.dp,y=7.dp)
                            ) {
                                Text(
                                    text = "×",
                                    fontSize = 30.sp,
                                    color = Color(0xFFA08963),
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .clickable { showCartPopup = false }
                                        .padding(4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                ) {
                                    Text(
                                        modifier = Modifier.offset(x = 70.dp, y = (-20).dp),
                                        text = if (cartItems.isNotEmpty()) "$totalItems Items" else "0 Items",
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                                        color = Color(0xFF706D54)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        modifier = Modifier.offset(x = 60.dp, y = (-20).dp),
                                        text = if (cartItems.isNotEmpty()) "Rp${String.format("%,d", totalPrice).replace(",", ".")}" else "Rp0",
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                                        color = Color(0xFFA08963)
                                    )
                                }

                                Button(
                                    onClick = {
                                        showCartPopup = false
                                        showConfirmOrderScreen = true
                                    },
                                    modifier = Modifier
                                        .height(44.dp)
                                        .offset(x = (-15).dp, (-20).dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF706D54)
                                    ),
                                    shape = RoundedCornerShape(200.dp)
                                ) {
                                    Text(
                                        text = "Checkout",
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
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