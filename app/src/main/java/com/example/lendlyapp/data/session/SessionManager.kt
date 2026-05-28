package com.example.lendlyapp.data.session

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences("lendly_auth_prefs", Context.MODE_PRIVATE)

    fun saveSession(token: String, userId: String) {
        prefs.edit()
            .putString("auth_token", token)
            .putString("user_id", userId)
            .apply()
    }

    fun getToken(): String? = prefs.getString("auth_token", null)

    fun isSessionActive(): Boolean = getToken() != null

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}