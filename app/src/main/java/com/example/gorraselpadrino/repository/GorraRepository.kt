package com.example.gorraselpadrino.repository

import com.example.gorraselpadrino.model.Gorra
import com.example.gorraselpadrino.model.CategoriaGorra

object GorraRepository {
    private val gorrasIniciales = listOf(
        Gorra(1, "New Era 59Fifty", 49990.0, "Gorra ajustable de calidad premium", CategoriaGorra.DEPORTIVA),
        Gorra(2, "Nike Heritage", 34990.0, "Gorra deportiva con tecnología Dri-FIT", CategoriaGorra.DEPORTIVA),
        Gorra(3, "Adidas Originals", 29990.0, "Gorra casual con logo clásico", CategoriaGorra.CASUAL),
        Gorra(4, "Puma Classic", 27990.0, "Gorra urbana estilo retro", CategoriaGorra.CASUAL),
        Gorra(5, "Stetson Premier", 89990.0, "Gorra elegante de lana fina", CategoriaGorra.ELEGANTE),
        Gorra(6, "Borsalino", 75990.0, "Gorra formal de alta costura", CategoriaGorra.ELEGANTE),
        Gorra(7, "Carhartt Acrylic", 38990.0, "Gorra unisex de trabajo resistente", CategoriaGorra.UNISEX),
        Gorra(8, "The North Face", 42990.0, "Gorra outdoor para todas las actividades", CategoriaGorra.UNISEX)
    )

    fun getGorras(): List<Gorra> = gorrasIniciales
}