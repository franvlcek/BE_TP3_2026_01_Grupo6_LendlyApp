package com.example.lendlyapp.data.repository

import com.example.lendlyapp.data.model.UserResponse
import com.example.lendlyapp.data.network.ApiService
import com.example.lendlyapp.data.session.SessionManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) {
    suspend fun getUserProfile(): Result<UserResponse> {
        val userId = sessionManager.getUserId() ?: return Result.failure(Exception("No user logged in"))
        
        // Si el ID es "0", es un usuario local recién registrado. No pedimos a la API.
        if (userId == "0") {
            android.util.Log.d("UserRepo", "Usuario local (ID 0). Saltando llamada a API.")
            return Result.failure(Exception("Local user"))
        }

        return try {
            android.util.Log.d("UserRepo", "Pidiendo perfil a la API para el ID: $userId")
            val response = apiService.getUserProfile(userId)
            android.util.Log.d("UserRepo", "¡Perfil recibido con éxito! Nombre: ${response.user?.fullName}")
            Result.success(response)
        } catch (e: Exception) {
            android.util.Log.e("UserRepo", "Error al obtener perfil: ${e.message}. Usando datos de sesión local.")
            Result.failure(e)
        }
    }
}
