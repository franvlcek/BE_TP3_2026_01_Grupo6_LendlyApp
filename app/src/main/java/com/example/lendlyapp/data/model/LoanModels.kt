package com.example.lendlyapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * DTO (Data Transfer Object) - Así viene de la API
 */
data class LoanDTO(
    @SerializedName("id") val id: String?,
    @SerializedName("companyName") val companyName: String?,
    @SerializedName("amount") val amount: Double?,
    @SerializedName("dueDate") val dueDate: String?,
    @SerializedName("status") val status: String?, // "Active", "Paid", etc.
    @SerializedName("installmentPlan") val installmentPlan: String? = null
)

/**
 * Wrapper para cuando la API devuelve un objeto en lugar de un Array directo
 */
data class LoansResponse(
    @SerializedName("loans") val loans: List<LoanDTO>?
)

/**
 * Request para solicitar préstamo
 */
data class LoanApplyRequest(
    @SerializedName("amount") val amount: Double,
    @SerializedName("installmentPlan") val installmentPlan: String,
    @SerializedName("purpose") val purpose: String
)

/**
 * Modelo de Dominio - Lo que usa tu UI (más limpio)
 */
data class LoanModel(
    val id: String,
    val company: String,
    val formattedAmount: String,
    val date: String,
    val isActive: Boolean
)

/**
 * Mapper: Convierte de DTO a Modelo de Dominio
 */
fun LoanDTO.toDomain(): LoanModel {
    return LoanModel(
        id = this.id ?: "N/A",
        company = this.companyName ?: "Unknown Company",
        formattedAmount = "₱ ${String.format("%.2f", this.amount ?: 0.0)}",
        date = this.dueDate ?: "No Date",
        isActive = (this.status ?: "").lowercase() == "active"
    )
}
