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

    /** Agrega un medicamento a la API y actualiza la lista local */
    fun addMedicamento(medicamento: Medicamento) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val nuevoMedicamento = repository.addMedicamento(medicamento)
                if (nuevoMedicamento != null) {
                    medicamentos.add(nuevoMedicamento)
                    // Recargar lista completa para asegurar sincronía
                    loadMedicamentos() 
                } else {
                    errorMessage.value = "Error al crear el medicamento"
                }
            } catch (e: Exception) {
                Log.e("CatalogoViewModel", "Error al agregar medicamento", e)
                errorMessage.value = "Error al agregar medicamento: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    /** Remueve un medicamento de la API y actualiza la lista local */
    fun removeMedicamento(medicamento: Medicamento) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val success = repository.deleteMedicamento(medicamento.id)
                if (success) {
                    medicamentos.remove(medicamento)
                } else {
                    errorMessage.value = "Error al eliminar el medicamento"
                }
            } catch (e: Exception) {
                Log.e("CatalogoViewModel", "Error al eliminar medicamento", e)
                errorMessage.value = "Error al eliminar medicamento: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    /** Actualiza un medicamento en la API y refresca la lista local */
    fun updateMedicamento(medicamentoActualizado: Medicamento) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val actualizado = repository.updateMedicamento(medicamentoActualizado)
                if (actualizado != null) {
                    val indice = medicamentos.indexOfFirst { it.id == medicamentoActualizado.id }
                    if (indice >= 0) {
                        medicamentos[indice] = actualizado
                    }
                } else {
                    errorMessage.value = "Error al actualizar el medicamento"
                }
            } catch (e: Exception) {
                Log.e("CatalogoViewModel", "Error al actualizar medicamento", e)
                errorMessage.value = "Error al actualizar medicamento: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}
