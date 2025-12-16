package com.example.gorraselpadrino.model

data class Pedido(
    val id: String,
    val cliente: String,
    val fecha: String,
    val total: Double,
    val direccion: String,
    val estado: String = "Completado"
)
