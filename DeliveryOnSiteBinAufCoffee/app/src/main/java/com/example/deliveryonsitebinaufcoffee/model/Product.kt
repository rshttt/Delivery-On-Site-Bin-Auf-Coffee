package com.example.deliveryonsitebinaufcoffee.model

// 1. Ganti import Moshi dengan import untuk Kotlinx Serialization
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CreateOrderRequest(
    @SerialName("delivery_code")
    val delivery_cost: Int = 0,

    @SerialName("user_id")
    val user_id: Int,

    @SerialName("code")
    val code: String,

    @SerialName("products")
    val products: List<ProductOrderItem>
)

@Serializable
data class ProductOrderItem(
    @SerialName("product_id")
    val product_id: Int,

    @SerialName("quantity")
    val quantity: Int
)

// Response model for order creation
@Serializable
data class OrderResponse(
    @SerialName("success")
    val success: Boolean = true,

    @SerialName("message")
    val message: String,

    @SerialName("invoice")
    val invoice: Invoice? = null
)

// Invoice model (if not already defined)
@Serializable
data class Invoice(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("code")
    val code: String,

    @SerialName("user_id")
    val user_id: Int? = null,

    @SerialName("delivery_cost")
    val delivery_cost: Int = 0,

    @SerialName("status")
    val status: String = "ready",

    @SerialName("created_at")
    val created_at: String? = null,

    @SerialName("updated_at")
    val updated_at: String? = null,

    @SerialName("products")
    val products: List<Product>? = null,

    @SerialName("user")
    val user: User? = null
)

// For API responses that might have different structure
@Serializable
data class InvoiceApiResponse(
    @SerialName("message")
    val message: String
)

@Serializable
data class ProductResponse(
    val products: List<Product>
)


// 2. Tambahkan anotasi @Serializable di atas data class
@Serializable
data class Product(
    // Sesuaikan field ini dengan nama kolom di database/respons JSON Laravel Anda
    val id: Int,
    val name: String,
    val price: Int,
    val description: String,
    val isFavorite: Boolean = false,

    // 3. Ganti anotasi @Json menjadi @SerialName
    @SerialName("image_path")
    val image_path: String?,

    @SerialName("category_id")
    val categoryId: Int,
)