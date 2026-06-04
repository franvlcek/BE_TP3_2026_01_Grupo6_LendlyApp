package com.example.lendlyapp.data.network

import com.example.lendlyapp.data.model.LoanApplyRequest
import com.example.lendlyapp.data.model.LoanDTO
import com.example.lendlyapp.data.model.LoansResponse
import com.example.lendlyapp.data.model.LoginRequest
import com.example.lendlyapp.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    // Probamos con LoansResponse en lugar de List directamente por el error BEGIN_OBJECT
    @GET("loans")
    suspend fun getLoans(): LoansResponse

    // Solicitar un nuevo préstamo
    @POST("loans/apply")
    suspend fun applyForLoan(@Body request: LoanApplyRequest): Any
}