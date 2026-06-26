package com.example.lendlyapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * BUENA PRÁCTICA: Data Transfer Object (DTO).
 * Representa exactamente la estructura del JSON que devuelve la API.
 * Usamos @SerializedName para mapear los nombres de la API a nuestras variables.
 */
data class LoanDTO(
    @SerializedName("id") val id: String?,
    @SerializedName("lender") val companyName: String?,
    @SerializedName("amount") val amount: Double?,
    @SerializedName("nextPaymentDate") val dueDate: String?,
    @SerializedName("nextPaymentLabel") val paymentLabel: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("installmentPlan") val installmentPlan: String? = null,
    @SerializedName("installmentAmount") val installmentAmount: Double? = null
)

/**
 * Wrapper de respuesta para manejar el formato de objeto de Postman Mock.
 */
data class LoansResponse(
    val success: Boolean,
    val loans: List<LoanDTO>?
)

data class LoanApplyResponse(
    val success: Boolean,
    val message: String,
    val loan: LoanDTO?
)

data class LoanApplyRequest(
    @SerializedName("amount") val amount: Double,
    @SerializedName("termMonths") val termMonths: Int,
    @SerializedName("purpose") val purpose: String
)

/**
 * MODELO DE DOMINIO: Es lo que la UI realmente necesita mostrar.
 * Está limpio de anotaciones de red y es más fácil de usar en Compose.
 */
data class LoanModel(
    val id: String,
    val company: String,
    val formattedAmount: String,
    val date: String,
    val isActive: Boolean
)

/**
 * MAPPER: El puente entre la API y la UI.
 * Transforma un DTO sucio en un Modelo de Dominio limpio.
 * Clave para la mantenibilidad: si la API cambia, solo se modifica esta función.
 */
fun LoanDTO.toDomain(): LoanModel {
    return LoanModel(
        id = this.id ?: "N/A",
        company = this.companyName ?: "Unknown Company",
        formattedAmount = "₱ ${String.format("%.2f", this.installmentAmount ?: 0.0)}",
        date = this.paymentLabel ?: "No Date",
        isActive = (this.status ?: "").uppercase() == "ACTIVE"
    )
}
