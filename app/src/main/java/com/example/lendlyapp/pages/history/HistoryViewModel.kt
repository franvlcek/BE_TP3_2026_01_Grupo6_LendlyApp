package com.example.lendlyapp.pages.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lendlyapp.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HistoryUiState>(HistoryUiState.Loading)
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadTransactions()
    }

    fun loadTransactions() {
        viewModelScope.launch {
            _uiState.value = HistoryUiState.Loading
            repository.getTransactions()
                .onSuccess { transactions ->
                    _uiState.value = HistoryUiState.Success(transactions)
                }
                .onFailure { error ->
                    _uiState.value = HistoryUiState.Error(error.message ?: "Unknown error")
                }
        }
    }
}

sealed class HistoryUiState {
    object Loading : HistoryUiState()
    data class Success(val transactions: List<Transaction>) : HistoryUiState()
    data class Error(val message: String) : HistoryUiState()
}
