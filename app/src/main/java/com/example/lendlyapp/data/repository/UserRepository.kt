package com.example.lendlyapp.data.repository

import com.example.lendlyapp.data.database.UserDao
import com.example.lendlyapp.data.database.UserEntity
import com.example.lendlyapp.data.mapper.toEntity
import com.example.lendlyapp.data.model.UserProfile
import com.example.lendlyapp.data.model.UserResponse
import com.example.lendlyapp.data.network.ApiService
import com.example.lendlyapp.data.session.SessionManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager,
    private val firestore: FirebaseFirestore,
    private val userDao: UserDao
) {
    // Escucha cambios del usuario en tiempo real desde Room
    fun getLocalUser(): Flow<UserEntity?> = userDao.getUser()

    /**
     * Obtiene el perfil del usuario. 
     * Prioriza Firestore para datos dinámicos, usa la API Mock como respaldo inicial.
     * Siempre actualiza Room con los datos obtenidos.
     */
    suspend fun getUserProfile(): Result<UserResponse> {
        val userId = sessionManager.getUserId() ?: return Result.failure(Exception("No user logged in"))
        
        return try {
            // Intentamos obtener el perfil desde Firestore (Datos reales centralizados)
            val document = firestore.collection("users").document(userId).get().await()
            
            val userProfile = if (document.exists()) {
                val user = document.toObject(UserProfile::class.java)
                android.util.Log.d("UserRepo", "Perfil cargado desde Firestore para: $userId")
                user
            } else {
                // Si no existe en Firestore (ej: usuario migrado o primer login), usamos la API Mock
                android.util.Log.d("UserRepo", "Documento no existe en Firestore. Usando API Mock.")
                val apiResponse = apiService.getUserProfile(userId)
                apiResponse.user
            }

            if (userProfile != null) {
                // Actualizar Room
                userDao.insertUser(userProfile.toEntity())
                // Si no estaba en Firestore, lo guardamos para la próxima
                if (!document.exists()) {
                    saveUserProfile(userProfile)
                }
                Result.success(UserResponse(success = true, user = userProfile))
            } else {
                Result.failure(Exception("User profile not found"))
            }
        } catch (e: Exception) {
            android.util.Log.e("UserRepo", "Error al obtener perfil: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Guarda o actualiza el perfil del usuario en Firestore y Room.
     */
    suspend fun saveUserProfile(profile: UserProfile): Result<Unit> {
        return try {
            // Actualizar Firestore
            firestore.collection("users").document(profile.id).set(profile).await()
            // Actualizar Room
            userDao.insertUser(profile.toEntity())
            android.util.Log.d("UserRepo", "Perfil guardado en Firestore y Room con éxito.")
            Result.success(Unit)
        } catch (e: Exception) {
            android.util.Log.e("UserRepo", "Error al guardar perfil: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Marca al usuario como verificado en Firestore, Room y localmente.
     */
    suspend fun verifyUser(): Result<Unit> {
        val userId = sessionManager.getUserId() ?: return Result.failure(Exception("No user logged in"))
        return try {
            // Firestore
            firestore.collection("users").document(userId).update("isVerified", true).await()
            
            // SessionManager (Local Prefs)
            sessionManager.setVerified(true)
            
            // Room (Base de datos local)
            val currentUser = userDao.getUserOnce()
            currentUser?.let { user ->
                val updatedUser = user.copy(isVerified = true)
                userDao.insertUser(updatedUser)
            }
            
            android.util.Log.d("UserRepo", "Usuario verificado en Firestore, Session y Room.")
            Result.success(Unit)
        } catch (e: Exception) {
            android.util.Log.e("UserRepo", "Error al verificar usuario: ${e.message}")
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
