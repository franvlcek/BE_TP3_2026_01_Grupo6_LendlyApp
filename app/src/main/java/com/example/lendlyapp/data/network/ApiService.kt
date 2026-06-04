package com.example.lendlyapp.data.network

import com.example.lendlyapp.data.model.LoginRequest
import com.example.lendlyapp.data.model.LoginResponse
import com.example.lendlyapp.data.model.ProductResponse
import com.example.lendlyapp.data.model.RegisterRequest
import com.example.lendlyapp.data.model.RegisterResponse
import com.example.lendlyapp.data.model.UserResponse
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

    @GET("transactions")
    suspend fun getTransactions(): TransactionResponse
}
