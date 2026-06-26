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

/**
 * VIEWMODEL: El "cerebro" de la vista de préstamos.
 * Mantiene el estado de la UI y sobrevive a cambios de configuración.
 */
@HiltViewModel
class LoanViewModel @Inject constructor(
    private val repository: LoanRepository
) : ViewModel() {

    // ESTADO DE LA UI: Representado con mutableStateOf para que Compose recomponga automáticamente.
    var loans by mutableStateOf<List<LoanModel>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    var loanAppliedSuccess by mutableStateOf(false)

    init {
        loadLoans()
    }

    /**
     * Llama al repositorio usando Corrutinas (viewModelScope).
     * Esto asegura que la llamada a la API no bloquee el hilo principal (UI Thread).
     */
    fun loadLoans() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.getLoans()
                .onSuccess { list ->
                    // La UI recibe data limpia
                    loans = list
                    isLoading = false
                }
                .onFailure { error ->
                    // Manejo centralizado de errores
                    errorMessage = "Error al cargar préstamos: ${error.message}"
                    isLoading = false
                }
        }
    }

    /**
     * Solicitud de préstamo interactiva.
     */
    fun applyForLoan(amount: Double, plan: String, purpose: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            
            // Lógica de negocio en el ViewModel: transformar el plan de cuotas
            val term = plan.split(" ")[0].toIntOrNull() ?: 12
            
            repository.applyForLoan(amount, term, purpose)
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
