package com.example.lendlyapp.data.network

import com.example.lendlyapp.data.model.*
import com.example.lendlyapp.pages.history.TransactionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/create")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @GET("users/{id}")
    suspend fun getUserProfile(@Path("id") userId: String): UserResponse

    @GET("products")
    suspend fun getProducts(): ProductResponse

    // Obtener préstamos activos e historial
    @GET("loans")
    suspend fun getLoans(): LoansResponse

    // Solicitar un nuevo préstamo
    @POST("loans/apply")
    suspend fun applyForLoan(@Body request: LoanApplyRequest): Any

    @GET("transactions")
    suspend fun getTransactions(): TransactionResponse
}
