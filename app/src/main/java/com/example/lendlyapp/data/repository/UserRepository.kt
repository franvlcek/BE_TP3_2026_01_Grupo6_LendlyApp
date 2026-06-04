package com.example.lendlyapp.data.repository

import com.example.lendlyapp.data.model.UserResponse
import com.example.lendlyapp.data.network.ApiService
import com.example.lendlyapp.data.session.SessionManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) {
    suspend fun getUserProfile(): Result<UserResponse> {
        val userId = sessionManager.getUserId() ?: return Result.failure(Exception("No user logged in"))
        return runCatching {
            apiService.getUserProfile(userId)
        }
    }
}
