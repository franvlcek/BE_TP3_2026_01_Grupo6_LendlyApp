package com.example.lendlyapp.data.repository

import com.example.lendlyapp.R
import com.example.lendlyapp.data.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor() {
    private val products = listOf(
        Product("1", "iPhone 12 Pro Max", "1,200", R.drawable.img_iphone, "Phone", "Apple", isRecommended = true, isBestSeller = true),
        Product("2", "Headphones", "500", R.drawable.img_headphones, "Headphones", "Sony", isRecommended = true),
        Product("3", "Nike Sneakers", "800", R.drawable.img_sneakers, "Apparel", "Nike", isRecommended = true, isBestSeller = true),
        Product("4", "Surface Laptop", "2,500", R.drawable.img_surface, "Apparel", "Microsoft", isBestSeller = true),
        Product("5", "PS4 Play Station", "1,500", R.drawable.img_ps4, "Electronics", "Sony", isBestSeller = true)
    )

    fun getAllProducts(): List<Product> = products
    
    fun getProductById(id: String): Product? = products.find { it.id == id }
    
    fun getRecommendedProducts(): List<Product> = products.filter { it.isRecommended }
    
    fun getBestSellers(): List<Product> = products.filter { it.isBestSeller }
}
