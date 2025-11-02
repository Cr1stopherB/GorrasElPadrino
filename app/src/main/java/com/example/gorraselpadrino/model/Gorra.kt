package com.example.gorraselpadrino.model

data class Gorra(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val categoria: CategoriaGorra
)

enum class CategoriaGorra {
    DEPORTIVA,
    CASUAL,
    ELEGANTE,
    UNISEX
}