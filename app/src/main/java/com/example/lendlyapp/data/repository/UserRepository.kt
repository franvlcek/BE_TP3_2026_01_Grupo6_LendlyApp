package com.example.lendlyapp.data.repository

import com.example.lendlyapp.data.database.UserDao
import com.example.lendlyapp.data.database.UserEntity
import com.example.lendlyapp.data.mapper.toEntity
import com.example.lendlyapp.data.model.UserProfile
import com.example.lendlyapp.data.model.UserResponse
import com.example.lendlyapp.data.network.ApiService
import com.example.lendlyapp.data.session.SessionManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager,
    private val userDao: UserDao
) {
    // Escucha cambios del usuario en tiempo real desde Room
    fun getLocalUser(): Flow<UserEntity?> = userDao.getUser()

    suspend fun getUserProfile(): Result<UserResponse> {
        val userId = sessionManager.getUserId() ?: return Result.failure(Exception("No user logged in"))
        
        if (userId == "0") {
            android.util.Log.d("UserRepo", "Usuario local (ID 0). Saltando llamada a API.")
            return Result.failure(Exception("Local user"))
        }

        return try {
            android.util.Log.d("UserRepo", "Pidiendo perfil a la API para el ID: $userId")
            val response = apiService.getUserProfile(userId)
            
            // Persistir en Room si la respuesta es exitosa
            response.user?.let { profile ->
                userDao.insertUser(profile.toEntity())
            }
            
            android.util.Log.d("UserRepo", "¡Perfil recibido con éxito! Nombre: ${response.user?.fullName}")
            Result.success(response)
        } catch (e: Exception) {
            android.util.Log.e("UserRepo", "Error al obtener perfil: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun saveUserLocally(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun clearLocalData() {
        userDao.deleteUser()
    }
}
