package com.example.gorraselpadrino.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gorraselpadrino.model.Medicamento
import com.example.gorraselpadrino.repository.MedicamentoRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para el catálogo de medicamentos Maneja el estado de carga, errores y la lista de
 * medicamentos
 */
class CatalogoViewModel : ViewModel() {
    // Repositorio para obtener datos de la API
    private val repository = MedicamentoRepository()

    // Lista mutable de medicamentos
    var medicamentos = mutableStateListOf<Medicamento>()
        private set

    // Estado de carga
    var isLoading = mutableStateOf(false)
        private set

    // Mensaje de error si ocurre algún problema
    var errorMessage = mutableStateOf<String?>(null)
        private set

    init {
        // Cargar medicamentos al inicializar el ViewModel
        loadMedicamentos()
    }

    /** Carga los medicamentos desde la API de eFarmaPlus */
    fun loadMedicamentos() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val listaMedicamentos = repository.getMedicamentos()
                medicamentos.clear()
                medicamentos.addAll(listaMedicamentos)

                if (listaMedicamentos.isEmpty()) {
                    errorMessage.value = "No se encontraron medicamentos"
                }
            } catch (e: Exception) {
                // Imprimir el error completo en Logcat para diagnóstico
                Log.e("CatalogoViewModel", "Error al cargar medicamentos", e)
                errorMessage.value = "Error al cargar medicamentos: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    /** Agrega un medicamento a la lista (para funcionalidad futura de admin) */
    fun addMedicamento(medicamento: Medicamento) {
        medicamentos.add(medicamento)
    }

    /** Remueve un medicamento de la lista (para funcionalidad futura de admin) */
    fun removeMedicamento(medicamento: Medicamento) {
        medicamentos.remove(medicamento)
    }

    /** Actualiza un medicamento existente (para funcionalidad futura de admin) */
    fun updateMedicamento(medicamentoActualizado: Medicamento) {
        val indice = medicamentos.indexOfFirst { it.id == medicamentoActualizado.id }
        if (indice >= 0) {
            medicamentos[indice] = medicamentoActualizado
        }
    }
}
