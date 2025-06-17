package com.example.deliveryonsitebinaufcoffee.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("email")
    val email: String,

)

@Serializable
data class ChangePasswordRequest(
    val email: String,
    val password: String,
    val password_confirmation: String
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class RegistrationRequest(
    @SerialName("name")
    val name: String,

    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,

    @SerialName("password_confirmation")
    val passwordConfirmation: String
)

@Serializable
data class LoginResponse(
    @SerialName("message")
    val message: String,

    @SerialName("user")
    val user: User,

    @SerialName("token")
    val token: String,

    @SerialName("token_type")
    val tokenType: String
)