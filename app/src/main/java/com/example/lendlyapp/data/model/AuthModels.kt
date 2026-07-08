package com.example.lendlyapp.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean = true,
    val token: String,
    val user: UserProfile
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val fullName: String,
    val phone: String
)

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val user: UserProfile? = null,
    val token: String? = null
)

data class UserResponse(
    val success: Boolean,
    val user: UserProfile?
)

data class UserProfile(
    val id: String, // Cambiado de Int a String para Firebase UIDs
    val fullName: String,
    val phone: String,
    val email: String,
    val avatar: String? = null,
    val birthDate: String? = null,
    val address: String? = null,
    val city: String? = null,
    val postalCode: String? = null,
    val creditScore: Int = 0,
    val creditLevel: String? = null,
    val availableBalance: Double = 0.0,
    val totalLoanLimit: Double = 0.0,
    val memberSince: String? = null,
    val isVerified: Boolean = false
)
