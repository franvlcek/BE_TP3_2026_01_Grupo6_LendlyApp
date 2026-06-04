package com.example.lendlyapp.pages.history

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("pagination") val pagination: Pagination,
    @SerializedName("transactions") val transactions: List<Transaction>
)

data class Pagination(
    @SerializedName("page") val page: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("hasNextPage") val hasNextPage: Boolean
)

data class Transaction(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("currency") val currency: String,
    @SerializedName("status") val status: String,
    @SerializedName("date") val date: String,
    @SerializedName("loanId") val loanId: String?,
    @SerializedName("referenceNumber") val referenceNumber: String?
)
