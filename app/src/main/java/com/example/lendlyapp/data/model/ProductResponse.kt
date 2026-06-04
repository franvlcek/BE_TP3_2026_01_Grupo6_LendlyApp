package com.example.lendlyapp.data.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    val success: Boolean,
    val products: List<ProductApi>
)

data class ProductApi(
    val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val price: Double,
    val image: String,
    val monthlyInstallment: Double,
    val isFeatured: Boolean
)
