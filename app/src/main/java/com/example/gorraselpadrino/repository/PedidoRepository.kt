package com.example.gorraselpadrino.repository

import com.example.gorraselpadrino.model.Medicamento
import com.example.gorraselpadrino.model.Pedido
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// --- Modelos para la API de Ventas/Pedidos ---

// Solicitud para crear una venta
data class VentaRequest(
    val total: Double,
    val productos: List<ProductoVentaRequest>,
    val direccionEntrega: String,
    val metodoPago: String
)

data class ProductoVentaRequest(
    val productoId: Int,
    val cantidad: Int
)

// Respuesta de la API (Venta/Pedido)
data class VentaAPI(
    val id: Int?,
    val fechaVenta: String?,
    val total: Double?,
    val direccionEntrega: String?,
    // La API podría devolver el usuario o no, dependiendo de la implementación
    val usuario: UsuarioSimpleAPI?,
    val productos: List<ProductoVentaAPI>?
)

data class UsuarioSimpleAPI(
    val nombreUsuario: String?
)

data class ProductoVentaAPI(
    val id: Int?,
    val nombre: String?,
    val precio: Double?
)

// Interfaz Retrofit
interface PedidosApiService {
    @GET("api/ventas")
    suspend fun getVentas(): List<VentaAPI>

    @POST("api/ventas")
    suspend fun createVenta(@Body venta: VentaRequest): VentaAPI
}

class PedidoRepository {
    private val baseUrl = "https://efarmaplusback.onrender.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(PedidosApiService::class.java)

    suspend fun getPedidos(): List<Pedido> {
        return try {
            val ventas = apiService.getVentas()
            ventas.map { venta ->
                Pedido(
                    id = venta.id?.toString() ?: "N/A",
                    cliente = venta.usuario?.nombreUsuario ?: "Cliente",
                    fecha = venta.fechaVenta ?: "",
                    total = venta.total ?: 0.0,
                    direccion = venta.direccionEntrega ?: "Sin dirección"
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun crearPedido(medicamentos: List<Medicamento>, total: Double, direccion: String, pago: String): Boolean {
        return try {
            // Transformar lista de medicamentos a formato API (agrupando por ID si fuera necesario, 
            // aqui asumimos que cada item en el carrito es una unidad o simplificamos)
            val productosRequest = medicamentos.mapNotNull { 
                val id = it.id.toIntOrNull()
                if (id != null) ProductoVentaRequest(id, 1) else null
            }

            if (productosRequest.isEmpty()) return false

            val request = VentaRequest(
                total = total,
                productos = productosRequest,
                direccionEntrega = direccion,
                metodoPago = pago
            )

            apiService.createVenta(request)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
