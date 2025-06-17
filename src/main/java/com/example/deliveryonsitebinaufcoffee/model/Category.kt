package com.example.deliveryonsitebinaufcoffee.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val categories: List<Category>
)

@Serializable
data class Category(
    val id: Int,
    val name: String,
    @SerialName("image_path")
    val image_path: String,
)