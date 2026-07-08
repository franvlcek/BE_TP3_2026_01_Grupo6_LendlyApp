package com.example.lendlyapp.data.repository

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

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val sessionManager: SessionManager
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

                sessionManager.saveSession(
                    token = token,
                    userId = user.uid,
                    fullName = profile?.fullName ?: (user.displayName ?: "User"),
                    email = user.email ?: "",
                    phone = profile?.phone ?: (user.phoneNumber ?: ""),
                    birthDate = profile?.birthDate,
                    address = profile?.address,
                    city = profile?.city,
                    postalCode = profile?.postalCode,
                    isVerified = profile?.isVerified ?: false,
                    availableBalance = profile?.availableBalance ?: 0.0
                )
                
                Result.success(LoginResponse(
                    success = true,
                    token = token,
                    user = profile ?: UserProfile(
                        id = user.uid, 
                        fullName = user.displayName ?: "User",
                        email = user.email ?: "",
                        phone = user.phoneNumber ?: ""
                    )
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

    fun logout() {
        firebaseAuth.signOut()
        sessionManager.clearSession()
    }
}
