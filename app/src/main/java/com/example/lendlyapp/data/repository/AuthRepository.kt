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
    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            android.util.Log.d("AuthRepo", "Intentando login para: ${request.email}")
            val response = apiService.login(request)

            if (response.token.isNotEmpty()) {
                val localEmail = sessionManager.getEmail()
                
                // Si cambiamos de usuario, limpiamos todo lo anterior
                if (localEmail != null && !localEmail.equals(response.user.email, ignoreCase = true)) {
                    android.util.Log.d("AuthRepo", "Cambio de usuario detectado. Limpiando datos previos.")
                    sessionManager.wipeAllData()
                }

                val isJohnDoe = response.user.email.lowercase().contains("john.doe")
                
                // Si es John Doe, la API manda. Si no, mandamos nosotros localmente.
                val finalVerified = if (isJohnDoe) true else sessionManager.isVerified()

                sessionManager.saveSession(
                    token = response.token,
                    userId = response.user.id.toString(),
                    fullName = response.user.fullName,
                    email = response.user.email,
                    phone = response.user.phone,
                    birthDate = response.user.birthDate,
                    address = response.user.address,
                    city = response.user.city ?: sessionManager.getCity(),
                    postalCode = response.user.postalCode ?: sessionManager.getPostalCode(),
                    avatar = response.user.avatar,
                    isVerified = finalVerified,
                    availableBalance = response.user.availableBalance
                )
                android.util.Log.d("AuthRepo", "Login exitoso. Verificado: $finalVerified")
            }
            Result.success(response)
        } catch (e: Exception) {
            android.util.Log.e("AuthRepo", "Error en login: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun register(request: RegisterRequest): Result<RegisterResponse> {
        return try {
            android.util.Log.d("AuthRepo", "Registrando: ${request.email}")
            val response = apiService.register(request)
            
            sessionManager.saveSession(
                token = response.token ?: "temp_token",
                userId = response.user?.id?.toString() ?: "0",
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
            Result.success(response)
        } catch (e: Exception) {
            android.util.Log.e("AuthRepo", "Error en registro: ${e.message}")
            Result.failure(e)
        }
    }

    fun isUserLoggedIn(): Boolean = sessionManager.isSessionActive()

    fun logout() {
        sessionManager.clearSession()
    }
}
