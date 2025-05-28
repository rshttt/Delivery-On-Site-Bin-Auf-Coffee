package com.example.deliveryonsitebinaufcoffee

class Category {
    data class CategoryItems(
        val categoryName: String,
        val image: Int
    )

    val category: List<CategoryItems>

    init {
        category = setCategory()
    }

    private fun setCategory(): List<CategoryItems> {
        return listOf(
            CategoryItems("Drink", R.drawable.minum),
            CategoryItems("Cake",  R.drawable.cake),
            CategoryItems("Snack", R.drawable.kentang),
            CategoryItems("Meal", R.drawable.meals)
        )
    }
}