package com.example.lendlyapp.data.repository

import com.example.lendlyapp.data.model.LoanApplyRequest
import com.example.lendlyapp.data.model.LoanModel
import com.example.lendlyapp.data.model.toDomain
import com.example.lendlyapp.data.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * REPOSITORIO: Única fuente de verdad de la data de préstamos.
 * BUENA PRÁCTICA: Abstrae la fuente de datos (podría ser Room o Retrofit).
 */
@Singleton
class LoanRepository @Inject constructor(
    private val apiService: ApiService // Inyección de dependencia vía Hilt
) {
    /**
     * Lógica de obtención de datos.
     * Encapsula la respuesta en un Result para un manejo de errores robusto.
     */
    suspend fun getLoans(): Result<List<LoanModel>> {
        return try {
            val response = apiService.getLoans()
            // Transformamos los DTOs a modelos de dominio antes de mandarlos al ViewModel
            val domainList = response.loans?.map { it.toDomain() } ?: emptyList()
            Result.success(domainList)
        } catch (e: Exception) {
            // Aquí capturamos errores de red o parsing
            Result.failure(e)
        }
    }

    /**
     * Lógica de solicitud de préstamo.
     */
    suspend fun applyForLoan(amount: Double, term: Int, purpose: String): Result<Boolean> {
        return try {
            val request = LoanApplyRequest(amount, term, purpose)
            val response = apiService.applyForLoan(request)
            Result.success(response.success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
