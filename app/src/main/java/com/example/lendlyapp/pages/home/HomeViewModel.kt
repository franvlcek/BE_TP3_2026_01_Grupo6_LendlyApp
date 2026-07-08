package com.example.lendlyapp.pages.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lendlyapp.data.model.UserProfile
import com.example.lendlyapp.data.repository.UserRepository
import com.example.lendlyapp.data.repository.TransactionRepository
import com.example.lendlyapp.data.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    var userProfile by mutableStateOf<UserProfile?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    // Balance inicial de la sesión
    var balance by mutableStateOf(sessionManager.getAvailableBalance())
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
                    if (response.user != null) {
                        balance = response.user.availableBalance
                    }
                }
            isLoading = false
        }
    }

    /**
     * Realiza un Cash-In y actualiza el estado local.
     */
    fun performCashIn(amount: Double, method: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            transactionRepository.performCashIn(amount, method)
                .onSuccess {
                    balance += amount
                    onSuccess()
                }
            isLoading = false
        }
    }
}
