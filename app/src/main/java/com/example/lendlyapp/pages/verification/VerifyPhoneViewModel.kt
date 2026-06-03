package com.example.lendlyapp.pages.verification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerifyPhoneViewModel @Inject constructor() : ViewModel() {

    var phoneNumber by mutableStateOf("")
        private set

    var countryCode by mutableStateOf("+65")
        private set

    var phoneError by mutableStateOf<String?>(null)
        private set

    fun onPhoneNumberChanged(newValue: String) {
        if (newValue.all { it.isDigit() }) {
            phoneNumber = newValue
            phoneError = null
        }
    }

    fun onCountryCodeChanged(newValue: String) {
        if (newValue.startsWith("+") && newValue.drop(1).all { it.isDigit() } || newValue.all { it.isDigit() }) {
            countryCode = newValue
            phoneError = null
        }
    }

    fun validate(onSuccess: () -> Unit) {
        if (phoneNumber.isNotBlank() && countryCode.isNotBlank()) {
            onSuccess()
        } else {
            phoneError = "Phone number is required"
        }
    }
}
