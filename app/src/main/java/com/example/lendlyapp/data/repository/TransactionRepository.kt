package com.example.lendlyapp.data.repository

import com.example.lendlyapp.data.network.ApiService
import com.example.lendlyapp.data.session.SessionManager
import com.example.lendlyapp.pages.history.Transaction
import com.example.lendlyapp.pages.history.TransactionResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val apiService: ApiService,
    private val firestore: FirebaseFirestore,
    private val sessionManager: SessionManager
) {
    /**
     * Obtiene el historial de transacciones.
     * Combina datos de Firestore y la API Mock.
     */
    suspend fun getTransactions(): Result<List<Transaction>> {
        val userId = sessionManager.getUserId() ?: return Result.failure(Exception("No user logged in"))
        return try {
            // 1. Obtener de Firestore
            val snapshot = firestore.collection("users").document(userId).collection("transactions")
                .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get().await()
            
            val firestoreTransactions = snapshot.documents.mapNotNull { doc ->
                Transaction(
                    id = doc.id,
                    type = doc.getString("type") ?: "CASH_IN",
                    title = doc.getString("title") ?: "Transaction",
                    description = doc.getString("description") ?: "",
                    amount = doc.getDouble("amount") ?: 0.0,
                    currency = doc.getString("currency") ?: "PHP",
                    status = doc.getString("status") ?: "COMPLETED",
                    date = doc.getTimestamp("date")?.toDate()?.toString() ?: "",
                    loanId = doc.getString("loanId"),
                    referenceNumber = doc.getString("referenceNumber")
                )
            }

            // 2. Obtener de API Mock
            val response: TransactionResponse = apiService.getTransactions()
            val apiTransactions = if (response.success) response.transactions else emptyList()

            // Combinamos (priorizando Firestore)
            Result.success(firestoreTransactions + apiTransactions)
        } catch (e: Exception) {
            android.util.Log.e("TransactionRepo", "Error al obtener transacciones: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Registra un Cash-In en Firestore y actualiza el balance.
     */
    suspend fun performCashIn(amount: Double, method: String): Result<Boolean> {
        val userId = sessionManager.getUserId() ?: return Result.failure(Exception("No user logged in"))
        return try {
            val transactionData = hashMapOf(
                "type" to "CASH_IN",
                "title" to "Cash In via $method",
                "description" to "Money added to your wallet",
                "amount" to amount,
                "currency" to "PHP",
                "status" to "COMPLETED",
                "date" to com.google.firebase.Timestamp.now(),
                "referenceNumber" to "REF${System.currentTimeMillis()}"
            )

            // 1. Guardar transacción
            firestore.collection("users").document(userId).collection("transactions").add(transactionData).await()

            // 2. Actualizar balance
            val userRef = firestore.collection("users").document(userId)
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(userRef)
                val currentBalance = snapshot.getDouble("availableBalance") ?: 0.0
                val newBalance = currentBalance + amount
                transaction.update(userRef, "availableBalance", newBalance)

                // Sincronizar sesión local
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
            android.util.Log.e("TransactionRepo", "Error en Cash-In: ${e.message}")
            Result.failure(e)
        }
    }
}
