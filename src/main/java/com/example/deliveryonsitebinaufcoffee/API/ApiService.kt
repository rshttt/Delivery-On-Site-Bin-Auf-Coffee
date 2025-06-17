package com.example.deliveryonsitebinaufcoffee.API

import com.example.deliveryonsitebinaufcoffee.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import com.example.deliveryonsitebinaufcoffee.model.ChangePasswordRequest

interface ApiService {
    @POST("login")
    @Headers("Accept: application/json")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("registration")
    @Headers("Accept: application/json")
    suspend fun register(@Body registrationRequest: RegistrationRequest): LoginResponse

    @POST("logout")
    @Headers("Accept: application/json")
    suspend fun logout(@Header("Authorization") token: String = "")

    @GET("user")
    @Headers("Accept: application/json")
    suspend fun getCurrentUser(@Header("Authorization") token: String = ""): User

    // User-specific endpoints
    @GET("user/favorites")
    @Headers("Accept: application/json")
    suspend fun getUserFavorites(@Header("Authorization") token: String = ""): ProductResponse

    @POST("user/reset-password")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun resetPassword(
        @Header("Authorization") token: String = "",
        @Body request: ChangePasswordRequest
    ): LoginResponse

    @Multipart
    @POST("user/reset-profile-image")
    @Headers("Accept: application/json")
    suspend fun updateProfileImage(
        @Header("Authorization") token: String = "",
        @Part image: MultipartBody.Part
    ): User

    // Product endpoints (require authentication)
    @GET("products")
    @Headers("Accept: application/json")
    suspend fun getProducts(@Header("Authorization") token: String = ""): ProductResponse

    @GET("products/{id}")
    @Headers("Accept: application/json")
    suspend fun getProductById(
        @Header("Authorization") token: String = "",
        @Path("id") productId: Int
    ): Product

    // Category endpoints (require authentication)
    @GET("categories")
    @Headers("Accept: application/json")
    suspend fun getCategories(@Header("Authorization") token: String = ""): CategoryResponse

    @POST("invoices")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Body orderRequest: CreateOrderRequest
    ): InvoiceApiResponse


}