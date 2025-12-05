package com.example.gorraselpadrino.model

data class Medicamento(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val imagenUrl: String,
    val categoria: String = "Medicamento",
    val laboratorio: String = "",
    val requiereReceta: Boolean = false,
    val codigoBarras: String = ""
)