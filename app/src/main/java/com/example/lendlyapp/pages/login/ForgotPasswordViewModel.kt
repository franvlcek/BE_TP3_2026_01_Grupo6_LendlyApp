package com.example.lendlyapp.pages.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor() : ViewModel() {

    var email by mutableStateOf("")
        private set

    var emailSent by mutableStateOf(false)
        private set

    fun onEmailChanged(newValue: String) {
        email = newValue
    }

    fun sendResetLink() {
        if (email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailSent = true
        }
    }
}
