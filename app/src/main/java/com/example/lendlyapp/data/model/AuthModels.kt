package com.example.lendlyapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo para enviar al endpoint POST /auth/login
 */
data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

/**
 * Modelo para recibir la respuesta del endpoint POST /auth/login
 */
data class LoginResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("token") val token: String,
    @SerializedName("user") val user: UserData
)

/**
 * Submodelo que contiene la información del usuario devuelta por la API
 */
data class UserData(
    @SerializedName("id") val id: Int, // Viene como Int en el JSON (id: 1)
    @SerializedName("fullName") val fullName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("creditScore") val creditScore: Int,
    @SerializedName("availableBalance") val availableBalance: Double,
    @SerializedName("memberSince") val memberSince: String
)