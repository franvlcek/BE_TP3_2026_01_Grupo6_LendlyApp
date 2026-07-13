package com.example.lendlyapp.pages.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lendlyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationDoneViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun finalizeRegistration(onDone: () -> Unit) {
        viewModelScope.launch {
            // Marcamos al usuario como verificado en Firestore y Room
            userRepository.verifyUser()
            onDone()
        }
    }
}
