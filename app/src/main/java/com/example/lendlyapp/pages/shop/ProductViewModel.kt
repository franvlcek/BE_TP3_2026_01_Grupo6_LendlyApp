package com.example.lendlyapp.pages.shop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lendlyapp.data.model.Product
import com.example.lendlyapp.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    var product by mutableStateOf<Product?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            isLoading = true
            // Intentamos buscarlo en el caché del repo
            val found = productRepository.getProductById(productId)
            if (found != null) {
                product = found
            } else {
                // Si no está (ej: link directo), cargamos todos
                productRepository.getAllProducts().onSuccess { products ->
                    product = products.find { it.id == productId }
                }
            }
            isLoading = false
        }
    }
}
