package com.example.lendlyapp.pages.verification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreatePasswordViewModel @Inject constructor() : ViewModel() {

    var password by mutableStateOf("")
        private set

    var passwordVisible by mutableStateOf(false)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    fun onPasswordChanged(newValue: String) {
        password = newValue
        passwordError = null
    }

    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun validate(onSuccess: () -> Unit) {
        val isValid = password.length >= 9 && 
                     password.any { it.isLetter() } && 
                     password.any { it.isDigit() }
        
        if (isValid) {
            onSuccess()
        } else {
            passwordError = "Password must be at least 9 characters, containing a letter and a number"
        }
    }
}
