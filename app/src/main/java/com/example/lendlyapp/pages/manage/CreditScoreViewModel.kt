package com.example.lendlyapp.pages.manage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lendlyapp.data.model.UserProfile
import com.example.lendlyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditScoreViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var userProfile by mutableStateOf<UserProfile?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            isLoading = true
            userRepository.getUserProfile()
                .onSuccess { response ->
                    userProfile = response.user
                }
            isLoading = false
        }
    }
}
