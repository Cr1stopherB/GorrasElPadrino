package com.example.gorraselpadrino.repository

import com.example.gorraselpadrino.model.Medicamento
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

// Modelo para crear/actualizar productos (lo que espera la API)
data class ProductoRequest(
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val idCategoria: Int, // Asumimos que la API espera IDs
    val idLaboratorio: Int, // Asumimos que la API espera IDs
    val idTipoFabricacion: Int, // Asumimos que la API espera IDs
    val requiereReceta: Boolean
)

// Interfaz de Retrofit para la API de eFarmaPlus
interface MedicamentoApiService {
    @GET("api/productos")
    suspend fun getMedicamentos(): List<ProductoAPI>

    @POST("api/productos")
    suspend fun createMedicamento(@Body producto: ProductoRequest): ProductoAPI

    @PUT("api/productos/{id}")
    suspend fun updateMedicamento(@Path("id") id: String, @Body producto: ProductoRequest): ProductoAPI

    @DELETE("api/productos/{id}")
    suspend fun deleteMedicamento(@Path("id") id: String)
}

// Repositorio para obtener medicamentos de la API
class MedicamentoRepository {
    private val baseUrl = "https://efarmaplusback.onrender.com/"

    // Configuración de Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
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
            mapProductoApiToMedicamento(producto)
        }
    }

    suspend fun addMedicamento(medicamento: Medicamento): Medicamento? {
        // Mapeo básico inverso: Medicamento -> ProductoRequest
        // NOTA: Esto es una simplificación. En un caso real necesitarías los IDs reales de categoria, lab, etc.
        // Aquí usaremos valores por defecto o simulados si no los tenemos en el modelo local.
        val request = ProductoRequest(
            nombre = medicamento.nombre,
            descripcion = medicamento.descripcion,
            precio = medicamento.precio,
            stock = medicamento.stock,
            idCategoria = 1, // TODO: Obtener ID real de categoría
            idLaboratorio = 1, // TODO: Obtener ID real de laboratorio
            idTipoFabricacion = 1, // TODO: Obtener ID real
            requiereReceta = medicamento.requiereReceta
        )
        
        return try {
            val response = apiService.createMedicamento(request)
            mapProductoApiToMedicamento(response)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateMedicamento(medicamento: Medicamento): Medicamento? {
        val request = ProductoRequest(
            nombre = medicamento.nombre,
            descripcion = medicamento.descripcion,
            precio = medicamento.precio,
            stock = medicamento.stock,
            idCategoria = 1, // TODO: Manejar IDs reales
            idLaboratorio = 1,
            idTipoFabricacion = 1,
            requiereReceta = medicamento.requiereReceta
        )

        return try {
            val response = apiService.updateMedicamento(medicamento.id, request)
            mapProductoApiToMedicamento(response)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun deleteMedicamento(id: String): Boolean {
        return try {
            apiService.deleteMedicamento(id)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun mapProductoApiToMedicamento(producto: ProductoAPI): Medicamento? {
        // Si el producto no tiene ID o nombre, lo descartamos para evitar errores
        if (producto.id == null || producto.nombre == null) return null

        // Manejo de URL de imagen
        val rawUrl = producto.imagenes?.firstOrNull()?.url 
        
        val finalUrl = if (!rawUrl.isNullOrBlank()) {
            if (rawUrl.startsWith("/")) {
                // Si es una ruta relativa, agregamos la URL base (quitando la barra final de base si es necesario)
                "${baseUrl.removeSuffix("/")}$rawUrl"
            } else {
                rawUrl
            }
        } else {
            "https://via.placeholder.com/300x300.png?text=Sin+Imagen"
        }

        return Medicamento(
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
