package com.example.lendlyapp.data.repository

import com.example.lendlyapp.data.model.LoanApplyRequest
import com.example.lendlyapp.data.model.LoanModel
import com.example.lendlyapp.data.model.toDomain
import com.example.lendlyapp.data.network.ApiService
import com.example.lendlyapp.data.session.SessionManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * REPOSITORIO: Única fuente de verdad de la data de préstamos.
 * BUENA PRÁCTICA: Abstrae la fuente de datos (podría ser Room o Retrofit).
 */
@Singleton
class LoanRepository @Inject constructor(
    private val apiService: ApiService,
    private val firestore: FirebaseFirestore,
    private val sessionManager: SessionManager
) {
    /**
     * Lógica de obtención de datos.
     * Prioriza Firestore para mostrar préstamos reales del usuario.
     */
    suspend fun getLoans(): Result<List<LoanModel>> {
        val userId = sessionManager.getUserId() ?: return Result.failure(Exception("No user logged in"))
        return try {
            // 1. Intentamos obtener préstamos de Firestore
            val snapshot = firestore.collection("users").document(userId).collection("loans")
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get().await()
            
            val firestoreLoans = snapshot.documents.mapNotNull { doc ->
                LoanModel(
                    id = doc.id,
                    company = doc.getString("company") ?: "Lendly",
                    formattedAmount = "₱ ${String.format(Locale.US, "%.2f", doc.getDouble("amount") ?: 0.0)}",
                    date = doc.getString("date") ?: "N/A",
                    isActive = doc.getBoolean("isActive") ?: true
                )
            }

            if (firestoreLoans.isNotEmpty()) {
                Result.success(firestoreLoans)
            } else {
                // 2. Si no hay en Firestore, traemos de la API (mock) como respaldo
                val response = apiService.getLoans()
                val domainList = response.loans?.map { it.toDomain() } ?: emptyList()
                Result.success(domainList)
            }
        } catch (e: Exception) {
            android.util.Log.e("LoanRepo", "Error al obtener préstamos: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Lógica de solicitud de préstamo.
     * Guarda el préstamo en Firestore y actualiza el saldo del usuario.
     */
    suspend fun applyForLoan(amount: Double, term: Int, purpose: String): Result<Boolean> {
        val userId = sessionManager.getUserId() ?: return Result.failure(Exception("No user logged in"))
        return try {
            // 1. Llamada a la API Mock (se mantiene por consigna)
            val request = LoanApplyRequest(amount, term, purpose)
            apiService.applyForLoan(request)

            // 2. Persistir el préstamo en la subcolección 'loans' del usuario
            val loanData = hashMapOf(
                "amount" to amount,
                "term" to term,
                "purpose" to purpose,
                "company" to "Rayland Finance",
                "date" to "Next Month",
                "isActive" to true,
                "createdAt" to com.google.firebase.Timestamp.now()
            )
            
            firestore.collection("users").document(userId).collection("loans").add(loanData).await()

            // 3. Actualizar balance del usuario en Firestore (Transacción atómica)
            val userRef = firestore.collection("users").document(userId)
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(userRef)
                val currentBalance = snapshot.getDouble("availableBalance") ?: 0.0
                val newBalance = currentBalance + amount
                transaction.update(userRef, "availableBalance", newBalance)
                
                // También actualizamos el balance en la sesión local
                sessionManager.saveSession(
                    token = sessionManager.getToken() ?: "",
                    userId = userId,
                    fullName = sessionManager.getFullName() ?: "",
                    email = sessionManager.getEmail() ?: "",
                    phone = sessionManager.getPhone() ?: "",
                    birthDate = sessionManager.getBirthDate(),
                    address = sessionManager.getAddress(),
                    city = sessionManager.getCity(),
                    postalCode = sessionManager.getPostalCode(),
                    avatar = sessionManager.getAvatar(),
                    isVerified = sessionManager.isVerified(),
                    availableBalance = newBalance
                )
            }.await()

            Result.success(true)
        } catch (e: Exception) {
            android.util.Log.e("LoanRepo", "Error al solicitar préstamo: ${e.message}")
            Result.failure(e)
        }
    }
}
