package com.example.lendlyapp.pages.shop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {

    var selectedBrand by mutableStateOf("All")
        private set
    var selectedGender by mutableStateOf("All")
        private set
    var selectedSort by mutableStateOf("Most Recent")
        private set
    var selectedPriceRange by mutableStateOf("All")
        private set

    fun onBrandSelected(newValue: String) {
        selectedBrand = newValue
    }

    fun onGenderSelected(newValue: String) {
        selectedGender = newValue
    }

    fun onSortSelected(newValue: String) {
        selectedSort = newValue
    }

    fun onPriceRangeSelected(newValue: String) {
        selectedPriceRange = newValue
    }

    fun resetFilters() {
        selectedBrand = "All"
        selectedGender = "All"
        selectedSort = "Most Recent"
        selectedPriceRange = "All"
    }
}
