package com.example.lendlyapp.data.repository

import com.example.lendlyapp.data.model.LoginRequest
import com.example.lendlyapp.data.model.LoginResponse
import com.example.lendlyapp.data.model.RegisterRequest
import com.example.lendlyapp.data.model.RegisterResponse
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
                val existingName = sessionManager.getFullName()
                val isJohnDoe = response.user.email == "john.doe@email.com"
                
                val finalName = if (isJohnDoe && !existingName.isNullOrBlank()) existingName else response.user.fullName
                
                // Si NO es John Doe, manejamos la verificación de forma puramente local
                val finalVerified = if (isJohnDoe) response.user.isVerified else sessionManager.isVerified()
                
                // Si es John Doe usamos el ID 1 de la API, sino mantenemos el ID local o 0
                val finalUserId = if (isJohnDoe) "1" else (sessionManager.getUserId() ?: "0")

                // Truco para el Mock: No sobreescribir datos locales con nulos de la API
                val finalCity = response.user.city ?: sessionManager.getCity()
                val finalPostalCode = response.user.postalCode ?: sessionManager.getPostalCode()

                sessionManager.saveSession(
                    token = response.token,
                    userId = finalUserId,
                    fullName = finalName,
                    email = response.user.email,
                    phone = response.user.phone,
                    birthDate = response.user.birthDate,
                    address = response.user.address,
                    city = finalCity,
                    postalCode = finalPostalCode,
                    avatar = response.user.avatar,
                    isVerified = finalVerified,
                    availableBalance = response.user.availableBalance
                )
            }

            response
        }
    }

    /**
     * Registra un nuevo usuario en la API.
     */
    suspend fun register(request: RegisterRequest): Result<RegisterResponse> {
        return try {
            android.util.Log.d("AuthRepo", "Intentando registrar usuario: ${request.email}")
            val response = apiService.register(request)
            
            // Persistimos los datos básicos para que el Login los reconozca después
            sessionManager.saveSession(
                token = "temp_token", // Token temporal hasta el login real
                userId = response.finalId ?: "0",
                fullName = request.fullName,
                email = request.email,
                phone = request.phone,
                birthDate = null,
                address = null,
                city = null,
                postalCode = null,
                avatar = null,
                isVerified = false,
                availableBalance = 0.0
            )
            
            android.util.Log.d("AuthRepo", "¡Registro exitoso y guardado localmente! ID: ${response.finalId}")
            Result.success(response)
        } catch (e: Exception) {
            android.util.Log.e("AuthRepo", "Fallo el registro en la API: ${e.message}")
            Result.failure(e)
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
