package com.example.lendlyapp.pages.manage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lendlyapp.data.model.UserProfile
import com.example.lendlyapp.data.repository.UserRepository
import com.example.lendlyapp.data.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
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
                    val apiUser = response.user
                    if (apiUser != null) {
                        // Priorizamos SIEMPRE los datos locales de sesión para Nombre y Email
                        // ya que el Mock de la API suele devolver datos estáticos (John Doe).
                        val localEmail = sessionManager.getEmail()
                        val localName = sessionManager.getFullName()
                        
                        userProfile = apiUser.copy(
                            fullName = if (!localName.isNullOrBlank()) localName else apiUser.fullName,
                            email = if (!localEmail.isNullOrBlank()) localEmail else apiUser.email,
                            city = sessionManager.getCity() ?: apiUser.city,
                            postalCode = sessionManager.getPostalCode() ?: apiUser.postalCode
                        )
                    }
                }
                .onFailure {
                    // Fallback to local session data if API fails
                    userProfile = UserProfile(
                        id = sessionManager.getUserId() ?: "0",
                        email = sessionManager.getEmail() ?: "",
                        fullName = sessionManager.getFullName() ?: "User",
                        phone = sessionManager.getPhone() ?: "",
                        address = sessionManager.getAddress(),
                        city = sessionManager.getCity(),
                        postalCode = sessionManager.getPostalCode()
                    )
                }
            isLoading = false
        }
    }

    fun logout(onSuccess: () -> Unit) {
        sessionManager.clearSession()
        onSuccess()
    }
}
