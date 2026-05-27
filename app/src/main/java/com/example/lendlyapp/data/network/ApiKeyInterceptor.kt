package com.example.lendlyapp.data.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Clonamos la petición original y le adosamos el header mandatorio del parcial
        val newRequest = originalRequest.newBuilder()
            .header("x-api-key", "123456789") // Requerimiento estricto de seguridad del PDF
            .build()

        return chain.proceed(newRequest)
    }
}