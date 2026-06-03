package com.example.lendlyapp.pages.shop

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lendlyapp.data.model.Product
import com.example.lendlyapp.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    var recommendedProducts by mutableStateOf<List<Product>>(emptyList())
        private set

    var bestSellers by mutableStateOf<List<Product>>(emptyList())
        private set

    init {
        loadData()
    }

    private fun loadData() {
        recommendedProducts = productRepository.getRecommendedProducts()
        bestSellers = productRepository.getBestSellers()
    }
}
