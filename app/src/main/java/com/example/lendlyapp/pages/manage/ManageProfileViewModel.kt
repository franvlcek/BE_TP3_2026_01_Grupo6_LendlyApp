package com.example.lendlyapp.pages.manage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lendlyapp.data.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var day by mutableStateOf("")
    var month by mutableStateOf("")
    var year by mutableStateOf("")
    var address by mutableStateOf("")
    var city by mutableStateOf("")
    var postalCode by mutableStateOf("")
    var phonePrefix by mutableStateOf("")
    var phoneNumber by mutableStateOf("")

    init {
        loadData()
    }

    private fun loadData() {
        val name = sessionManager.getFullName() ?: ""
        val nameParts = name.split(" ")
        firstName = nameParts.getOrNull(0) ?: ""
        lastName = nameParts.getOrNull(1) ?: ""

        val birthDate = sessionManager.getBirthDate() ?: ""
        val dateParts = birthDate.split("/")
        day = dateParts.getOrNull(0) ?: ""
        month = dateParts.getOrNull(1) ?: ""
        year = dateParts.getOrNull(2) ?: ""

        address = sessionManager.getAddress() ?: ""
        // City and Postal code are not explicitly in SessionManager yet, 
        // but we can infer them or just load from what we have.
        
        val phone = sessionManager.getPhone() ?: ""
        if (phone.contains("-")) {
            val phoneParts = phone.split("-")
            phonePrefix = phoneParts.getOrNull(0) ?: ""
            phoneNumber = phoneParts.getOrNull(1) ?: ""
        } else {
            phonePrefix = "+65"
            phoneNumber = phone
        }
    }

    fun saveChanges(onSuccess: () -> Unit) {
        val fullName = "$firstName $lastName".trim()
        val birthDate = "$day/$month/$year"
        val phone = if (phoneNumber.isNotBlank()) "$phonePrefix-$phoneNumber" else ""

        sessionManager.saveSession(
            token = sessionManager.getToken() ?: "",
            userId = sessionManager.getUserId() ?: "",
            fullName = fullName,
            email = sessionManager.getEmail() ?: "",
            phone = phone,
            birthDate = birthDate,
            address = address,
            avatar = sessionManager.getAvatar()
        )
        onSuccess()
    }
}
