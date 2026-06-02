package com.example.lendlyapp.data.model

data class Product(
    val id: String,
    val name: String,
    val price: String,
    val imageResId: Int,
    val category: String,
    val brand: String = "",
    val isBestSeller: Boolean = false,
    val isRecommended: Boolean = false
)
