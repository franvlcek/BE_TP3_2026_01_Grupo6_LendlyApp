package com.example.lendlyapp.pages.loan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lendlyapp.data.model.LoanModel
import com.example.lendlyapp.data.repository.LoanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanViewModel @Inject constructor(
    private val repository: LoanRepository
) : ViewModel() {

    // Estado de la lista de préstamos
    var loans by mutableStateOf<List<LoanModel>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    // Estado de la solicitud de préstamo
    var loanAppliedSuccess by mutableStateOf(false)

    init {
        loadLoans()
    }

    /**
     * Llama al repositorio para traer los préstamos reales de la API
     */
    fun loadLoans() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.getLoans()
                .onSuccess { list ->
                    loans = list
                    isLoading = false
                }
                .onFailure { error ->
                    errorMessage = "Error al cargar préstamos: ${error.message}"
                    isLoading = false
                }
        }
    }

    /**
     * Envía la solicitud de préstamo a la API
     */
    fun applyForLoan(amount: Double, plan: String, purpose: String) {
        viewModelScope.launch {
            isLoading = true
            repository.applyForLoan(amount, plan, purpose)
                .onSuccess {
                    loanAppliedSuccess = true
                    isLoading = false
                }
                .onFailure { error ->
                    errorMessage = "Error al solicitar préstamo: ${error.message}"
                    isLoading = false
                }
        }
    }
}
