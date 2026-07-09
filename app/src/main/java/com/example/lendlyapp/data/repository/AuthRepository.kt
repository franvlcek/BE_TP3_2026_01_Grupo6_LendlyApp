package com.example.lendlyapp.data.repository

import com.example.lendlyapp.data.database.UserDao
import com.example.lendlyapp.data.model.LoginRequest
import com.example.lendlyapp.data.model.LoginResponse
import com.example.lendlyapp.data.model.RegisterRequest
import com.example.lendlyapp.data.model.RegisterResponse
import com.example.lendlyapp.data.model.UserProfile
import com.example.lendlyapp.data.session.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import com.example.lendlyapp.data.mapper.toEntity

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val sessionManager: SessionManager,
    private val userDao: UserDao
) {
    /**
     * Intenta iniciar sesión con Firebase Auth.
     */
    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            android.util.Log.d("AuthRepo", "Intentando login Firebase para: ${request.email}")
            
            val authResult = firebaseAuth.signInWithEmailAndPassword(request.email, request.password).await()
            val user = authResult.user

            if (user != null) {
                val token = user.getIdToken(false).await().token ?: ""
                
                // Intentamos recuperar datos adicionales de Firestore
                val doc = firestore.collection("users").document(user.uid).get().await()
                val profile = if (doc.exists()) {
                    doc.toObject(UserProfile::class.java)
                } else null

                val finalProfile = profile ?: UserProfile(
                    id = user.uid, 
                    fullName = user.displayName ?: "User",
                    email = user.email ?: "",
                    phone = user.phoneNumber ?: ""
                )

                sessionManager.saveSession(
                    token = token,
                    userId = user.uid,
                    fullName = finalProfile.fullName,
                    email = finalProfile.email,
                    phone = finalProfile.phone,
                    birthDate = finalProfile.birthDate,
                    address = finalProfile.address,
                    city = finalProfile.city,
                    postalCode = finalProfile.postalCode,
                    isVerified = finalProfile.isVerified,
                    availableBalance = finalProfile.availableBalance
                )

                // Persistir en Room (Cache local)
                userDao.insertUser(finalProfile.toEntity())

                android.util.Log.d("AuthRepo", "Login exitoso. Perfil guardado en Room.")
                
                Result.success(LoginResponse(
                    success = true,
                    token = token,
                    user = finalProfile
                ))
            } else {
                Result.failure(Exception("Usuario nulo tras login"))
            }
        } catch (e: Exception) {
            android.util.Log.e("AuthRepo", "Error en login Firebase: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Registra un nuevo usuario en Firebase Auth y crea su documento en Firestore.
     */
    suspend fun register(request: RegisterRequest): Result<RegisterResponse> {
        return try {
            android.util.Log.d("AuthRepo", "Registrando en Firebase: ${request.email}")
            
            val authResult = firebaseAuth.createUserWithEmailAndPassword(request.email, request.password).await()
            val user = authResult.user
            
            if (user != null) {
                val newProfile = UserProfile(
                    id = user.uid,
                    fullName = request.fullName,
                    email = request.email,
                    phone = request.phone,
                    isVerified = false,
                    availableBalance = 2500.0 // Saldo inicial de cortesía
                )

                // Guardamos el perfil en Firestore (Centralizado)
                firestore.collection("users").document(user.uid).set(newProfile).await()

                sessionManager.saveSession(
                    token = "temp_token",
                    userId = user.uid,
                    fullName = request.fullName,
                    email = request.email,
                    phone = request.phone,
                    birthDate = null,
                    address = null,
                    isVerified = false,
                    availableBalance = 2500.0
                )
                
                // Persistir en Room
                userDao.insertUser(newProfile.toEntity())

                Result.success(RegisterResponse(
                    success = true, 
                    message = "Success", 
                    user = newProfile,
                    token = "temp_token"
                ))
            } else {
                Result.failure(Exception("Error al crear usuario"))
            }
        } catch (e: Exception) {
            android.util.Log.e("AuthRepo", "Error en registro Firebase: ${e.message}")
            Result.failure(e)
        }
    }

    fun isUserLoggedIn(): Boolean = firebaseAuth.currentUser != null

    suspend fun logout() {
        firebaseAuth.signOut()
        sessionManager.clearSession()
        userDao.deleteUser()
    }

    fun getUser() = userDao.getUser()
}
