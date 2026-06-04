package com.example.lendlyapp.data.repository

import com.example.lendlyapp.data.network.ApiService
import com.example.lendlyapp.pages.history.Transaction
import com.example.lendlyapp.pages.history.TransactionResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getTransactions(): Result<List<Transaction>> {
        return try {
            val response: TransactionResponse = apiService.getTransactions()
            if (response.success) {
                Result.success(response.transactions)
            } else {
                Result.failure(Exception("Failed to fetch transactions"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
