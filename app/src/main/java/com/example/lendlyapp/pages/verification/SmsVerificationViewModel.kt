package com.example.lendlyapp.pages.verification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SmsVerificationViewModel @Inject constructor() : ViewModel() {

    var code by mutableStateOf(listOf("", "", "", "", "", ""))
        private set

    var showError by mutableStateOf(false)
        private set

    fun onCodeChanged(index: Int, newValue: String, onNextFocus: () -> Unit, onClearFocus: () -> Unit) {
        if (newValue.all { it.isDigit() }) {
            if (newValue.isEmpty()) {
                val newCode = code.toMutableList()
                newCode[index] = ""
                code = newCode
                // Custom back focus logic usually handled in UI with FocusRequester
            } else if (newValue.length <= 1) {
                val newCode = code.toMutableList()
                newCode[index] = newValue
                code = newCode
                showError = false

                if (index < 5) {
                    onNextFocus()
                } else {
                    onClearFocus()
                }
            } else {
                val newCode = code.toMutableList()
                newCode[index] = newValue.last().toString()
                code = newCode
                if (index < 5) onNextFocus()
            }
        }
    }

    fun onBackspace(index: Int, onPreviousFocus: () -> Unit) {
        if (code[index].isEmpty() && index > 0) {
            onPreviousFocus()
        }
    }

    fun validate(onSuccess: () -> Unit) {
        if (code.all { it.isNotEmpty() }) {
            onSuccess()
        } else {
            showError = true
        }
    }
}
