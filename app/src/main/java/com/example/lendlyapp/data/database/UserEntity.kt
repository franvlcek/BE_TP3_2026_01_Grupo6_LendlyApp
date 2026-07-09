package com.example.lendlyapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val fullName: String,
    val phone: String,
    val email: String,
    val avatar: String,
    val birthDate: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val creditScore: Int,
    val creditLevel: String,
    val availableBalance: Double,
    val totalLoanLimit: Double,
    val memberSince: String,
    val isVerified: Boolean
)
