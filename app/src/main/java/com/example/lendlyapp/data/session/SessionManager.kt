package com.example.lendlyapp.data.session

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences("lendly_auth_prefs", Context.MODE_PRIVATE)
    private val _tokenFlow = MutableStateFlow(getToken())
    val tokenFlow: StateFlow<String?> = _tokenFlow

    private val _userIdFlow = MutableStateFlow(getUserId())
    val userIdFlow: StateFlow<String?> = _userIdFlow

    fun saveSession(token: String, userId: String) {
        prefs.edit {
            putString("auth_token", token)
            putString("user_id", userId)
        }
        _tokenFlow.value = token
        _userIdFlow.value = userId
    }

    fun getToken(): String? = prefs.getString("auth_token", null)
    fun getUserId(): String? = prefs.getString("user_id", null)

    fun isSessionActive(): Boolean = getToken() != null


    fun clearSession() {
        prefs.edit { clear() }
        _tokenFlow.value = null
        _userIdFlow.value = null
    }
}
