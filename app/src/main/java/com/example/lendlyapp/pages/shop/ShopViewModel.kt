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
class ShopViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    var recommendedProducts by mutableStateOf<List<Product>>(emptyList())
        private set

    var bestSellers by mutableStateOf<List<Product>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            
            val result = productRepository.getAllProducts()
            
            result.onSuccess { products ->
                recommendedProducts = products.filter { it.isRecommended }
                bestSellers = products.filter { it.isBestSeller }
            }.onFailure { exception ->
                errorMessage = "Error al cargar productos: ${exception.localizedMessage}"
            }
            
            isLoading = false
        }
    }
}
