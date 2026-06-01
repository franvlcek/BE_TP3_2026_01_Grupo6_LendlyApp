package com.example.lendlyapp.data.repository

import com.example.lendlyapp.data.model.LoginRequest
import com.example.lendlyapp.data.model.LoginResponse
import com.example.lendlyapp.data.network.ApiService
import com.example.lendlyapp.data.session.SessionManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) {
    /**
     * Intenta iniciar sesión con la API.
     * Si es exitoso, guarda el token e ID localmente en el dispositivo.
     */
    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return runCatching {
            // Llamamos a la API (Retrofit se encarga de suspender el hilo de fondo)
            val response = apiService.login(request)

            // Si la respuesta fue exitosa y trae el token, lo persistimos en SharedPreferences
            if (response.token.isNotEmpty()) {
                sessionManager.saveSession(
                    token = response.token,
                    userId = response.user.id.toString(),
                    fullName = response.user.fullName,
                    email = response.user.email,
                    phone = response.user.phone,
                    birthDate = response.user.birthDate,
                    address = response.user.address,
                    avatar = response.user.avatar
                )
            }

            response
        }
    }

    /**
     * Verifica de forma rápida si el usuario ya está logueado en este dispositivo.
     */
    fun isUserLoggedIn(): Boolean {
        return sessionManager.isSessionActive()
    }

    /**
     * Borra los datos del dispositivo para cerrar la sesión.
     */
    fun logout() {
        sessionManager.clearSession()
    }
}