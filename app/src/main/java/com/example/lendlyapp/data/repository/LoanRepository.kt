package com.example.lendlyapp.data.repository

import com.example.lendlyapp.data.model.LoanApplyRequest
import com.example.lendlyapp.data.model.LoanModel
import com.example.lendlyapp.data.model.toDomain
import com.example.lendlyapp.data.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoanRepository @Inject constructor(
    private val apiService: ApiService
) {
    /**
     * Trae la lista de préstamos y los mapea a modelos de dominio
     */
    suspend fun getLoans(): Result<List<LoanModel>> {
        return try {
            val response = apiService.getLoans()
            Result.success(response.loans.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Envía la solicitud de un nuevo préstamo
     */
    suspend fun applyForLoan(amount: Double, plan: String, purpose: String): Result<Boolean> {
        return try {
            val request = LoanApplyRequest(amount, plan, purpose)
            apiService.applyForLoan(request)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
