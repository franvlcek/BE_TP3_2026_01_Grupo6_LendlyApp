package com.example.lendlyapp.data.repository

import com.example.lendlyapp.data.model.UserProfile
import com.example.lendlyapp.data.model.UserResponse
import com.example.lendlyapp.data.network.ApiService
import com.example.lendlyapp.data.session.SessionManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager,
    private val firestore: FirebaseFirestore
) {
    /**
     * Obtiene el perfil del usuario. 
     * Prioriza Firestore para datos dinámicos, usa la API Mock como respaldo inicial.
     */
    suspend fun getUserProfile(): Result<UserResponse> {
        val userId = sessionManager.getUserId() ?: return Result.failure(Exception("No user logged in"))
        
        return try {
            // Intentamos obtener el perfil desde Firestore (Datos reales centralizados)
            val document = firestore.collection("users").document(userId).get().await()
            
            if (document.exists()) {
                val user = document.toObject(UserProfile::class.java)
                android.util.Log.d("UserRepo", "Perfil cargado desde Firestore para: $userId")
                Result.success(UserResponse(success = true, user = user))
            } else {
                // Si no existe en Firestore (ej: usuario migrado o primer login), usamos la API Mock
                android.util.Log.d("UserRepo", "Documento no existe en Firestore. Usando API Mock.")
                val apiResponse = apiService.getUserProfile(userId)
                // Guardamos en Firestore para la próxima vez
                apiResponse.user?.let { saveUserProfile(it) }
                Result.success(apiResponse)
            }
        } catch (e: Exception) {
            android.util.Log.e("UserRepo", "Error al obtener perfil: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Guarda o actualiza el perfil del usuario en Firestore.
     */
    suspend fun saveUserProfile(profile: UserProfile): Result<Unit> {
        return try {
            firestore.collection("users").document(profile.id).set(profile).await()
            android.util.Log.d("UserRepo", "Perfil guardado en Firestore con éxito.")
            Result.success(Unit)
        } catch (e: Exception) {
            android.util.Log.e("UserRepo", "Error al guardar en Firestore: ${e.message}")
            Result.failure(e)
        }
    }
}
