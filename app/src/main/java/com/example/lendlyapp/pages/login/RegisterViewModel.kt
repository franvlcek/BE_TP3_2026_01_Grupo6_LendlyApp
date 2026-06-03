package com.example.lendlyapp.pages.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

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
            onSuccess()
        }
    }
}
