package com.example.lendlyapp.data.model

import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("currency") val currency: String,
    @SerializedName("type") val type: String, // e.g., "payment", "added"
    @SerializedName("company") val company: String,
    @SerializedName("time") val time: String,
    @SerializedName("date") val date: String,
    @SerializedName("transactionNumber") val transactionNumber: String,
    @SerializedName("fee") val fee: Double
)