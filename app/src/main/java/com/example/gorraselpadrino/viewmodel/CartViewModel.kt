package com.example.gorraselpadrino.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.gorraselpadrino.model.Medicamento

/** ViewModel para el carrito de compras Maneja la lista de medicamentos agregados al carrito */
class CartViewModel : ViewModel() {
    // Lista de medicamentos en el carrito
    var medicamentosEnCarrito = mutableStateListOf<Medicamento>()
        private set

    /** Agrega un medicamento al carrito */
    fun addMedicamentoAlCarrito(medicamento: Medicamento) {
        medicamentosEnCarrito.add(medicamento)
    }

    /** Remueve un medicamento del carrito */
    fun removeMedicamentoDelCarrito(medicamento: Medicamento) {
        medicamentosEnCarrito.remove(medicamento)
    }

    /** Limpia el carrito */
    fun limpiarCarrito() {
        medicamentosEnCarrito.clear()
    }

    /** Calcula el total del carrito */
    fun calcularTotal(): Double {
        return medicamentosEnCarrito.sumOf { it.precio }
    }
}
