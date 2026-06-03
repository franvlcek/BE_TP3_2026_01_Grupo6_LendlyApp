package com.example.lendlyapp.pages.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lendlyapp.data.model.LoginRequest
import com.example.lendlyapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

// Anotación clave para que Hilt sepa cómo construir este ViewModel
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    // Estados mutables que la pantalla (Compose) va a "observar" en tiempo real
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var loginSuccess by mutableStateOf(false)
        private set

    // Funciones para actualizar lo que el usuario escribe en los campos de texto
    fun onEmailChanged(newEmail: String) {
        email = newEmail
        errorMessage = null // Limpiamos el error si empieza a escribir de nuevo
    }

    fun onPasswordChanged(newPassword: String) {
        password = newPassword
        errorMessage = null
    }

    /**
     * Ejecuta el proceso de Login interactuando con el repositorio.
     * Al usar viewModelScope.launch, nos metemos en una corrutina de Kotlin
     * para que la app no se congele mientras espera la respuesta de internet.
     */
    fun onLoginClicked() {
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Por favor, completa todos los campos."
            return
        } else if (!emailPattern.matcher(email).matches()) {
            errorMessage = "El formato del correo electrónico es inválido."
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val request = LoginRequest(email = email, password = password)
            val result = authRepository.login(request)

            result.onSuccess {
                loginSuccess = true
            }
            result.onFailure { exception ->

                errorMessage = when (exception) {
                    is IOException -> "Error de conexión. Revisa tu internet."
                    is HttpException -> {
                        when (exception.code()) {
                            401 -> "Correo o contraseña incorrectos."
                            403 -> "Acceso prohibido. Contacta a soporte."
                            404 -> "El servidor de login no fue encontrado."
                            500 -> "Error en el servidor. Inténtalo más tarde."
                            else -> "Problema en el servidor (${exception.code()})"
                        }
                    }
                    else -> exception.localizedMessage ?: "Ocurrió un error inesperado."
                }
            }

            isLoading = false
        }
    }
}