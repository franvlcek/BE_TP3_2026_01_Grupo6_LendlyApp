package com.example.lendlyapp.data.network

import com.example.lendlyapp.data.model.LoginRequest
import com.example.lendlyapp.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    // El PDF especifica que el inicio de sesión es un POST a /auth/login y retorna el token
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}