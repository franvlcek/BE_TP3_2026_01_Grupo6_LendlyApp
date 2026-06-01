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

    fun saveSession(
        token: String,
        userId: String,
        fullName: String,
        email: String,
        phone: String,
        birthDate: String?,
        address: String?,
        avatar: String
    ) {
        prefs.edit {
            putString("auth_token", token)
            putString("user_id", userId)
            putString("full_name", fullName)
            putString("email", email)
            putString("phone", phone)
            putString("birth_date", birthDate)
            putString("address", address)
            putString("avatar", avatar)
        }
        _tokenFlow.value = token
        _userIdFlow.value = userId
    }

    fun getToken(): String? = prefs.getString("auth_token", null)
    fun getUserId(): String? = prefs.getString("user_id", null)
    fun getFullName(): String? = prefs.getString("full_name", null)
    fun getEmail(): String? = prefs.getString("email", null)

    fun getPhone(): String? = prefs.getString("phone", null)

    fun getBirthDate(): String? = prefs.getString("birth_date", null)

    fun getAddress(): String? = prefs.getString("address", null)
    fun getAvatar(): String? = prefs.getString("avatar", null)
    fun isSessionActive(): Boolean = getToken() != null


    fun clearSession() {
        prefs.edit { clear() }
        _tokenFlow.value = null
        _userIdFlow.value = null
    }
}
