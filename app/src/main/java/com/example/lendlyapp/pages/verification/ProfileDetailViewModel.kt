package com.example.lendlyapp.pages.verification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ProfileDetailViewModel @Inject constructor() : ViewModel() {

    var firstName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var day by mutableStateOf("")
        private set
    var month by mutableStateOf("")
        private set
    var year by mutableStateOf("")
        private set
    var address by mutableStateOf("")
        private set
    var city by mutableStateOf("")
        private set
    var postalCode by mutableStateOf("")
        private set
    var phone by mutableStateOf("")
        private set

    var firstNameError by mutableStateOf<String?>(null)
        private set
    var lastNameError by mutableStateOf<String?>(null)
        private set
    var dateError by mutableStateOf<String?>(null)
        private set
    var addressError by mutableStateOf<String?>(null)
        private set
    var cityError by mutableStateOf<String?>(null)
        private set
    var postalCodeError by mutableStateOf<String?>(null)
        private set
    var phoneError by mutableStateOf<String?>(null)
        private set

    fun onFirstNameChanged(newValue: String) {
        firstName = newValue
        firstNameError = null
    }

    fun onLastNameChanged(newValue: String) {
        lastName = newValue
        lastNameError = null
    }

    fun onDayChanged(newValue: String) {
        if (newValue.all { it.isDigit() } && newValue.length <= 2) {
            day = newValue
            dateError = null
        }
    }

    fun onMonthChanged(newValue: String) {
        if (newValue.all { it.isDigit() } && newValue.length <= 2) {
            month = newValue
            dateError = null
        }
    }

    fun onYearChanged(newValue: String) {
        if (newValue.all { it.isDigit() } && newValue.length <= 4) {
            year = newValue
            dateError = null
        }
    }

    fun onAddressChanged(newValue: String) {
        address = newValue
        addressError = null
    }

    fun onCityChanged(newValue: String) {
        city = newValue
        cityError = null
    }

    fun onPostalCodeChanged(newValue: String) {
        if (newValue.all { it.isDigit() }) {
            postalCode = newValue
            postalCodeError = null
        }
    }

    fun onPhoneChanged(newValue: String) {
        if (newValue.all { it.isDigit() }) {
            phone = newValue
            phoneError = null
        }
    }

    fun validate(onSuccess: () -> Unit) {
        var hasError = false

        if (firstName.isBlank()) {
            firstNameError = "First name is required"
            hasError = true
        }
        if (lastName.isBlank()) {
            lastNameError = "Last name is required"
            hasError = true
        }

        if (day.isBlank() || month.isBlank() || year.isBlank()) {
            dateError = "Date of birth is required"
            hasError = true
        } else {
            val d = day.toIntOrNull() ?: 0
            val m = month.toIntOrNull() ?: 0
            val y = year.toIntOrNull() ?: 0

            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH) + 1
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

            if (d !in 1..31 || m !in 1..12 || y < 1900) {
                dateError = "Invalid date"
                hasError = true
            } else if (y > currentYear || (y == currentYear && m > currentMonth) || (y == currentYear && m == currentMonth && d > currentDay)) {
                dateError = "Date cannot be in the future"
                hasError = true
            }
        }

        if (address.isBlank()) {
            addressError = "Address is required"
            hasError = true
        }
        if (city.isBlank()) {
            cityError = "City is required"
            hasError = true
        }
        if (postalCode.isBlank()) {
            postalCodeError = "Postal code is required"
            hasError = true
        }
        if (phone.isBlank()) {
            phoneError = "Phone number is required"
            hasError = true
        }

        if (!hasError) {
            onSuccess()
        }
    }
}
