package com.example.gorraselpadrino.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.gorraselpadrino.model.Gorra

class CartViewModel : ViewModel() {
    var carrito = mutableStateListOf<Gorra>()
        private set

    fun addGorraAlCarrito(gorra: Gorra) {
        carrito.add(gorra)
    }

    fun clearCart() {
        carrito.clear()
    }
}