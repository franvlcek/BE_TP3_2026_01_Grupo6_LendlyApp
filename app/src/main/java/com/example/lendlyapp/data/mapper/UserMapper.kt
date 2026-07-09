package com.example.lendlyapp.data.mapper

import com.example.lendlyapp.data.database.UserEntity
import com.example.lendlyapp.data.model.UserProfile

fun UserProfile.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        fullName = fullName,
        phone = phone,
        email = email,
        avatar = avatar ?: "",
        birthDate = birthDate ?: "",
        address = address ?: "",
        city = city ?: "",
        postalCode = postalCode ?: "",
        creditScore = creditScore,
        creditLevel = creditLevel ?: "",
        availableBalance = availableBalance,
        totalLoanLimit = totalLoanLimit,
        memberSince = memberSince ?: "",
        isVerified = isVerified
    )
}
