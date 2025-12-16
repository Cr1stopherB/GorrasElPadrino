package com.example.gorraselpadrino.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gorraselpadrino.repository.PedidoRepository
import com.example.gorraselpadrino.model.Pedido
import kotlinx.coroutines.launch

class AdminOrdersViewModel : ViewModel() {
    private val repository = PedidoRepository()

    var pedidos = mutableStateListOf<Pedido>()
        private set

    var isLoading = mutableStateOf(false)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    init {
        loadPedidos()
    }

    fun loadPedidos() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val result = repository.getPedidos()
                pedidos.clear()
                pedidos.addAll(result)
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }
}
