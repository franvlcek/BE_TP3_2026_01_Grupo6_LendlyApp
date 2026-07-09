package com.example.lendlyapp.pages.manage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lendlyapp.data.model.UserProfile
import com.example.lendlyapp.data.session.SessionManager
import com.example.lendlyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository
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
        val dateParts = if (birthDate.contains("-")) birthDate.split("-") else birthDate.split("/")
        
        if (birthDate.contains("-")) {
            // Formato YYYY-MM-DD
            year = dateParts.getOrNull(0) ?: ""
            month = dateParts.getOrNull(1) ?: ""
            day = dateParts.getOrNull(2) ?: ""
        } else {
            // Formato DD/MM/YYYY
            day = dateParts.getOrNull(0) ?: ""
            month = dateParts.getOrNull(1) ?: ""
            year = dateParts.getOrNull(2) ?: ""
        }

        address = sessionManager.getAddress() ?: ""
        city = sessionManager.getCity() ?: ""
        postalCode = sessionManager.getPostalCode() ?: ""
        
        val phone = sessionManager.getPhone() ?: ""
        if (phone.contains("-")) {
            val phoneParts = phone.split("-")
            phonePrefix = phoneParts.getOrNull(0) ?: ""
            phoneNumber = phoneParts.getOrNull(1) ?: ""
        } else {
            phonePrefix = "+63" // Cambiado a Filipinas (+63) dado el contexto de pesos filipinos
            phoneNumber = phone
        }

    }

    fun saveChanges(onSuccess: () -> Unit) {
        val fullName = "$firstName $lastName".trim()
        val birthDate = "$day/$month/$year"
        val phone = if (phoneNumber.isNotBlank()) "$phonePrefix-$phoneNumber" else ""

        val updatedProfile = UserProfile(
            id = sessionManager.getUserId() ?: "",
            fullName = fullName,
            email = sessionManager.getEmail() ?: "",
            phone = phone,
            birthDate = birthDate,
            address = address,
            city = city,
            postalCode = postalCode,
            avatar = sessionManager.getAvatar(),
            isVerified = sessionManager.isVerified(),
            availableBalance = sessionManager.getAvailableBalance()
        )

        viewModelScope.launch {
            // Sincronizamos con Firestore y Room
            userRepository.saveUserProfile(updatedProfile)

            // Guardamos localmente en SessionManager (para compatibilidad con el resto del flujo)
            sessionManager.saveSession(
                token = sessionManager.getToken() ?: "",
                userId = updatedProfile.id,
                fullName = updatedProfile.fullName,
                email = updatedProfile.email,
                phone = updatedProfile.phone,
                birthDate = updatedProfile.birthDate,
                address = updatedProfile.address,
                city = updatedProfile.city,
                postalCode = updatedProfile.postalCode,
                avatar = updatedProfile.avatar,
                isVerified = updatedProfile.isVerified,
                availableBalance = updatedProfile.availableBalance
            )
            onSuccess()
        }
    }
}
