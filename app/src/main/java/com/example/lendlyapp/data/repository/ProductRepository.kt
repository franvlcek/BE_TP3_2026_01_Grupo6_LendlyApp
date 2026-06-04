package com.example.lendlyapp.data.repository

import com.example.lendlyapp.R
import com.example.lendlyapp.data.model.Product
import com.example.lendlyapp.data.network.ApiService
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val apiService: ApiService
) {
    private val defaultProducts = listOf(
        Product("1", "iPhone 12 Pro Max", "1,200", imageResId = R.drawable.img_iphone, category = "Phone", brand = "Apple", isRecommended = true, isBestSeller = true),
        Product("2", "Headphones", "500", imageResId = R.drawable.img_headphones, category = "Headphones", brand = "Sony", isRecommended = true),
        Product("3", "Nike Sneakers", "800", imageResId = R.drawable.img_sneakers, category = "Apparel", brand = "Nike", isRecommended = true, isBestSeller = true),
        Product("4", "Surface Laptop", "2,500", imageResId = R.drawable.img_surface, category = "Apparel", brand = "Microsoft", isBestSeller = true),
        Product("5", "PS4 Play Station", "1,500", imageResId = R.drawable.img_ps4, category = "Electronics", brand = "Sony", isBestSeller = true)
    )

    private var cachedProducts: List<Product> = defaultProducts

    suspend fun getAllProducts(): Result<List<Product>> {
        return try {
            android.util.Log.d("ProductRepo", "Intentando conectar con la API Mock...")
            val response = apiService.getProducts()
            if (response.success && response.products.isNotEmpty()) {
                android.util.Log.d("ProductRepo", "¡Éxito! Se cargaron ${response.products.size} productos desde la API.")
                cachedProducts = response.products.map { apiProd ->
                    Product(
                        id = apiProd.id,
                        name = apiProd.name,
                        price = String.format(Locale.US, "%.0f", apiProd.monthlyInstallment), // Usamos cuota como precio para la UI
                        imageUrl = apiProd.image,
                        category = apiProd.category,
                        brand = apiProd.brand,
                        isRecommended = apiProd.isFeatured,
                        isBestSeller = !apiProd.isFeatured // Lógica arbitraria para fallback
                    )
                }
            } else {
                android.util.Log.w("ProductRepo", "La API respondió vacío o sin éxito. Manteniendo datos actuales.")
            }
            Result.success(cachedProducts)
        } catch (e: Exception) {
            android.util.Log.e("ProductRepo", "Error en la llamada a la API: ${e.message}. Usando fallback local.")
            Result.success(cachedProducts)
        }
    }
    
    fun getProductById(id: String): Product? = cachedProducts.find { it.id == id }
    
    suspend fun getRecommendedProducts(): List<Product> {
        if (cachedProducts.isEmpty()) getAllProducts()
        return cachedProducts.filter { it.isRecommended }
    }
    
    suspend fun getBestSellers(): List<Product> {
        if (cachedProducts.isEmpty()) getAllProducts()
        return cachedProducts.filter { it.isBestSeller }
    }
}
