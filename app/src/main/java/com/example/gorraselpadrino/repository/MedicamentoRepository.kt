package com.example.gorraselpadrino.repository

import com.example.gorraselpadrino.model.Medicamento
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Modelos API más flexibles (campos nullables para evitar crashes por datos faltantes)
data class CategoriaAPI(
    val id: Int?,
    val nombreCategoria: String?
)

data class LaboratorioAPI(
    val id: Int?,
    val nombre: String?
)

data class ImagenAPI(
    val id: Int?,
    val url: String?
)

data class TipoFabricacionAPI(
    val id: Int?,
    val nombre: String?
)

data class ProductoAPI(
    val id: Int?,
    val nombre: String?,
    val descripcion: String?,
    val precio: Double?,
    val stock: Int?,
    val requiereReceta: Boolean?,
    val categoria: CategoriaAPI?,
    val laboratorio: LaboratorioAPI?,
    val tipoFabricacion: TipoFabricacionAPI?,
    val imagenes: List<ImagenAPI>?
)

// Interfaz de Retrofit para la API de eFarmaPlus
interface MedicamentoApiService {
    @GET("api/productos")
    suspend fun getMedicamentos(): List<ProductoAPI>
}

// Repositorio para obtener medicamentos de la API
class MedicamentoRepository {
    private val BASE_URL = "https://efarmaplusback.onrender.com/"

    // Configuración de Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    private val apiService = retrofit.create(MedicamentoApiService::class.java)
    
    /**
     * Obtiene la lista de medicamentos desde la API de eFarmaPlus
     * Mapea los productos de la API al modelo Medicamento de la app
     */
    suspend fun getMedicamentos(): List<Medicamento> {
        val productos = apiService.getMedicamentos()
        
        return productos.mapNotNull { producto ->
            // Si el producto no tiene ID o nombre, lo descartamos para evitar errores
            if (producto.id == null || producto.nombre == null) return@mapNotNull null

            // Manejo de URL de imagen
            var rawUrl = producto.imagenes?.firstOrNull()?.url 
            
            val finalUrl = if (!rawUrl.isNullOrBlank()) {
                if (rawUrl.startsWith("/")) {
                    // Si es una ruta relativa, agregamos la URL base (quitando la barra final de base si es necesario)
                    "${BASE_URL.removeSuffix("/")}$rawUrl"
                } else {
                    rawUrl
                }
            } else {
                "https://via.placeholder.com/300x300.png?text=Sin+Imagen"
            }

            Medicamento(
                id = producto.id.toString(),
                nombre = producto.nombre,
                descripcion = producto.descripcion ?: "Sin descripción",
                precio = producto.precio ?: 0.0,
                stock = producto.stock ?: 0,
                imagenUrl = finalUrl,
                categoria = producto.categoria?.nombreCategoria ?: "General",
                laboratorio = producto.laboratorio?.nombre ?: "Desconocido",
                requiereReceta = producto.requiereReceta ?: false,
                codigoBarras = "" 
            )
        }
    }
}
