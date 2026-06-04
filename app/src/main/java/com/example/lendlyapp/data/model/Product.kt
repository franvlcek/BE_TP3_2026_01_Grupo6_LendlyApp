package com.example.lendlyapp.data.model

data class Product(
    val id: String,
    val name: String,
    val price: String,
    val imageUrl: String? = null,
    val imageResId: Int? = null,
    val category: String,
    val brand: String = "",
    val isBestSeller: Boolean = false,
    val isRecommended: Boolean = false
)
