package com.example.lendlyapp.pages.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lendlyapp.data.model.RegisterRequest
import com.example.lendlyapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var passwordVisible by mutableStateOf(false)
        private set

    var emailError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    var confirmPasswordError by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var generalError by mutableStateOf<String?>(null)
        private set

    fun onEmailChanged(newValue: String) {
        email = newValue
        emailError = null
    }

    fun onPasswordChanged(newValue: String) {
        password = newValue
        passwordError = null
    }

    fun onConfirmPasswordChanged(newValue: String) {
        confirmPassword = newValue
        confirmPasswordError = null
    }

    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun validate(onSuccess: () -> Unit) {
        var hasError = false
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS

        if (email.isBlank()) {
            emailError = "Email is required"
            hasError = true
        } else if (!emailPattern.matcher(email).matches()) {
            emailError = "Invalid email format"
            hasError = true
        }

        if (password.isBlank()) {
            passwordError = "Password is required"
            hasError = true
        } else if (password.length < 6) {
            passwordError = "Password must be at least 6 characters"
            hasError = true
        }

        if (confirmPassword != password) {
            confirmPasswordError = "Passwords do not match"
            hasError = true
        }

        if (!hasError) {
            viewModelScope.launch {
                isLoading = true
                generalError = null
                
                // En un flujo real, aquí pasaríamos los datos recolectados. 
                // Por ahora usamos placeholders para los campos que la API pide pero no están en esta pantalla.
                val request = RegisterRequest(
                    email = email,
                    password = password,
                    fullName = "New User", // Placeholder inicial
                    phone = "000000000"
                )
                
                authRepository.register(request)
                    .onSuccess { 
                        onSuccess()
                    }
                    .onFailure { exception ->
                        generalError = "Error al crear cuenta: ${exception.localizedMessage}"
                    }
                
                isLoading = false
            }
        }
    }
}
