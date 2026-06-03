package com.example.lendlyapp.pages.shop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    val recentSearches = mutableStateListOf(
        "Blue shirt", "Red shirt", "Yellow shirt", "Blue Shoes",
        "Yellow Shoes", "Red Shoes", "Yellow Shoes", "Red Shoes",
        "Blue Shoes", "Yellow shirt"
    )

    fun onSearchQueryChanged(newValue: String) {
        searchQuery = newValue
    }

    fun onRecentSearchClicked(search: String) {
        searchQuery = search
    }

    fun removeRecentSearch(search: String) {
        recentSearches.remove(search)
    }

    fun clearAllRecent() {
        recentSearches.clear()
    }
}
