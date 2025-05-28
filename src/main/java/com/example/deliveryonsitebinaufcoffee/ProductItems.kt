package com.example.deliveryonsitebinaufcoffee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductItems : ViewModel() {
    data class Product(
        val name: String,
        val price: Int,
        val image: Int,
        val categoryName: Category.CategoryItems,
        var isFavorite: Boolean = false
    )

    private val categoryList = Category().category

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _categories = MutableStateFlow(categoryList)
    val categories: StateFlow<List<Category.CategoryItems>> = _categories.asStateFlow()

    init {
        setProduct()
    }

    private fun setProduct() {
        viewModelScope.launch {
            val productItem = listOf(
                Product("Espresso Classic", 15000, R.drawable.minum, categoryList[0]),
                Product("Sego Goreng", 18000, R.drawable.meals, categoryList[3]),
                Product("Chocolate Yummy", 25000, R.drawable.minum, categoryList[0]),
                Product("Strawberry Doughnut", 10000, R.drawable.cake, categoryList[1])
            )
            _products.value = productItem
        }
    }

    fun toggleFavorite(index: Int) {
        _products.value = _products.value.toMutableList().also {
            val product = it[index]
            it[index] = product.copy(isFavorite = !product.isFavorite)
        }
    }
}