package com.example.deliveryonsitebinaufcoffee.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryonsitebinaufcoffee.API.RetrofitInstance
import com.example.deliveryonsitebinaufcoffee.CartItem
import com.example.deliveryonsitebinaufcoffee.model.Category
import com.example.deliveryonsitebinaufcoffee.model.CreateOrderRequest
import com.example.deliveryonsitebinaufcoffee.model.OrderResponse
import com.example.deliveryonsitebinaufcoffee.model.Product
import com.example.deliveryonsitebinaufcoffee.model.ProductOrderItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import kotlin.random.Random

data class ProductUiState(
    val product: Product,
    var isFavorite: Boolean = false
)

class ProductViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<ProductUiState>>(emptyList())
    val products: StateFlow<List<ProductUiState>> = _products.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isSubmittingOrder = MutableStateFlow(false)
    val isSubmittingOrder: StateFlow<Boolean> = _isSubmittingOrder.asStateFlow()

    private val _orderSubmissionResult = MutableStateFlow<OrderResponse?>(null)
    val orderSubmissionResult: StateFlow<OrderResponse?> = _orderSubmissionResult.asStateFlow()

    // Store the auth token properly
    private var _authToken = MutableStateFlow<String?>(null)

    init {
        // Don't fetch automatically - wait for token to be set
    }

    // Initialize with token from MainActivity
    fun initializeWithToken(token: String) {
        if (_authToken.value == null && token.isNotEmpty()) {
            setAuthToken(token)
        }
    }

    fun setAuthToken(token: String) {
        _authToken.value = token
        // Ensure token has Bearer prefix
        val formattedToken = if (token.startsWith("Bearer ")) token else "Bearer $token"
        // Fetch data once token is set
        fetchProducts(formattedToken)
        fetchCategories(formattedToken)
    }

    private fun fetchProducts(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // Ensure token has Bearer prefix
                val authToken = if (token.startsWith("Bearer ")) token else "Bearer $token"
                val productResponse = RetrofitInstance.api.getProducts(authToken)

                println("=== PRODUCTS FROM API ===")
                productResponse.products.forEachIndexed { index, product ->
                    println("[$index] Product Name: ${product.name}")
                    println("[$index] Category ID: ${product.categoryId}")
                    println("[$index] Price: ${product.price}")
                    println("---")
                }
                println("=========================")

                val productUiStates = productResponse.products.map { product ->
                    ProductUiState(product = product, isFavorite = false)
                }
                _products.value = productUiStates

                println("Categories available: ${_categories.value.size}")
                _categories.value.forEach { cat ->
                    println("Category: ${cat.id} - ${cat.name}")
                }

            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                _errorMessage.value = when (e.code()) {
                    401 -> "Authentication failed. Please login again."
                    422 -> "Invalid request data: ${errorBody ?: e.message()}"
                    else -> "Server error (${e.code()}): ${e.message()}"
                }
                e.printStackTrace()
            } catch (e: IOException) {
                _errorMessage.value = "Network error. Please check your connection."
                e.printStackTrace()
            } catch (e: Exception) {
                _errorMessage.value = "An unexpected error occurred: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    internal fun fetchCategories(token: String) {
        viewModelScope.launch {
            try {
                // Ensure token has Bearer prefix
                val authToken = if (token.startsWith("Bearer ")) token else "Bearer $token"
                val categoryResponse = RetrofitInstance.api.getCategories(authToken)

                println("=== CATEGORIES FROM API ===")
                categoryResponse.categories.forEachIndexed { index, category ->
                    println("[$index] Name: ${category.name}")
                    println("[$index] ImagePath: '${category.image_path}'")
                    println("---")
                }
                println("===========================")

                _categories.value = categoryResponse.categories
            } catch (e: HttpException) {
                _errorMessage.value = when (e.code()) {
                    401 -> "Authentication failed. Please login again."
                    else -> "Failed to load categories: ${e.message}"
                }
                e.printStackTrace()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load categories: ${e.message}"
                e.printStackTrace()
            }
        }
    }

    fun toggleFavorite(productId: Int) {
        _products.update { currentProducts ->
            currentProducts.map { productUiState ->
                if (productUiState.product.id == productId) {
                    productUiState.copy(isFavorite = !productUiState.isFavorite)
                } else {
                    productUiState
                }
            }
        }
    }

    fun refresh() {
        _authToken.value?.let { token ->
            val formattedToken = if (token.startsWith("Bearer ")) token else "Bearer $token"
            fetchProducts(formattedToken)
            fetchCategories(formattedToken)
        } ?: run {
            _errorMessage.value = "No authentication token available. Please login."
        }
    }

    // Generate 5-character alphanumeric code as expected by Laravel
    private fun generateOrderCode(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..5)  // 5 characters as expected by Laravel
            .map { chars[Random.nextInt(chars.length)] }
            .joinToString("")
    }

    private fun parseValidationErrors(errorBody: String?): String {
        return try {
            val json = JSONObject(errorBody ?: "{}")
            val errors = json.optJSONObject("errors")

            if (errors != null) {
                val errorMessages = mutableListOf<String>()
                errors.keys().forEach { key ->
                    val errorArray = errors.getJSONArray(key)
                    for (i in 0 until errorArray.length()) {
                        errorMessages.add("$key: ${errorArray.getString(i)}")
                    }
                }
                return errorMessages.joinToString("\n")
            }

            // If there's a message field
            json.optString("message", "Invalid request data")
        } catch (e: Exception) {
            errorBody ?: "Invalid request data"
        }
    }

    fun submitOrder(
        cartItems: List<CartItem>,
        userId: Int,
        additionalNotes: String,
        deliveryOption: String,
        paymentMethod: String,
        pickupDay: String? = null,
        pickupTime: String? = null,
        authToken: String
    ) {
        viewModelScope.launch {
            _isSubmittingOrder.value = true
            _errorMessage.value = null

            try {
                // Ensure authToken has Bearer prefix
                val formattedToken = if (authToken.startsWith("Bearer ")) {
                    authToken
                } else {
                    "Bearer $authToken"
                }

                // Validate inputs
                if (formattedToken.isEmpty() || formattedToken == "Bearer ") {
                    _errorMessage.value = "Invalid authentication token"
                    return@launch
                }

                if (userId <= 0) {
                    _errorMessage.value = "Invalid user ID"
                    return@launch
                }

                if (cartItems.isEmpty()) {
                    _errorMessage.value = "Cart is empty"
                    return@launch
                }

                // Generate 5-character alphanumeric code as expected by Laravel
                val orderCode = generateOrderCode()
                val totalCost = cartItems.sumOf { it.totalPrice }

                // Log the request data for debugging
                println("=== ORDER REQUEST DEBUG ===")
                println("Auth Token: ${if (formattedToken.length > 20) formattedToken.take(20) + "..." else formattedToken}")
                println("User ID: $userId")
                println("Order Code: $orderCode")
                println("Total Cost: $totalCost")
                println("Delivery Option: $deliveryOption")
                println("Payment Method: $paymentMethod")
                println("Additional Notes: ${additionalNotes.ifEmpty { "None" }}")
                println("Pickup Day: ${pickupDay ?: "Not set"}")
                println("Pickup Time: ${pickupTime ?: "Not set"}")
                println("Cart Items:")
                cartItems.forEach { item ->
                    println("  - ${item.product.name} (ID: ${item.product.id})")
                    println("    Qty: ${item.quantity}")
                    println("    Size: ${item.size}, Sugar: ${item.sugar}, Ice: ${item.ice}")
                    println("    Item Total: ${item.totalPrice}")
                }
                println("=========================")

                // Create products array matching Laravel's expected structure
                // Fixed: Use correct parameter name based on ProductOrderItem constructor
                val products = cartItems.map { cartItem ->
                    ProductOrderItem(
                        product_id = cartItem.product.id,  // Fixed: use product_id instead of productId
                        quantity = cartItem.quantity
                    )
                }

                // Create order request matching Laravel controller
                // Fixed: Remove deliveryCost parameter if it doesn't exist in constructor
                val orderRequest = CreateOrderRequest(
                    code = orderCode,
                    products = products,
                    user_id = userId
                )

                // Log the final request for debugging
                println("=== FINAL ORDER REQUEST ===")
                println("Order Code: ${orderRequest.code}")
                println("Products: ${orderRequest.products.size} items")
                orderRequest.products.forEach { product ->
                    println("  - Product ID: ${product.product_id}, Qty: ${product.quantity}")  // Fixed: use product_id
                }
                println("=========================")

                // Make the API call
                val response = RetrofitInstance.api.createOrder(formattedToken, orderRequest)

                // Handle the response
                if (response.message.contains("berhasil", ignoreCase = true)) {
                    _orderSubmissionResult.value = OrderResponse(
                        success = true,
                        message = response.message,
                    )
                    println("Order submitted successfully!")
                    println("Response: ${response.message}")

                    // TODO: Store additional order details locally if needed
                    // (additionalNotes, deliveryOption, paymentMethod, pickupDay, pickupTime)
                    // These can be stored in local database or SharedPreferences

                } else {
                    _errorMessage.value = response.message
                    println("Order submission failed: ${response.message}")
                }

            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                println("HTTP Error ${e.code()}")
                println("Error Body: $errorBody")

                _errorMessage.value = when (e.code()) {
                    401 -> "Authentication failed. Please login again."
                    422 -> {
                        val detailedError = parseValidationErrors(errorBody)
                        println("Validation errors: $detailedError")
                        "Validation error:\n$detailedError"
                    }
                    else -> "Server error (${e.code()}): ${e.message()}"
                }
                e.printStackTrace()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to submit order: ${e.message}"
                println("Exception: ${e.message}")
                e.printStackTrace()
            } finally {
                _isSubmittingOrder.value = false
            }
        }
    }

    fun clearOrderResult() {
        _orderSubmissionResult.value = null
        _errorMessage.value = null
    }
}