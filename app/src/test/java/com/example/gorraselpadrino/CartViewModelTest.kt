package com.example.gorraselpadrino

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gorraselpadrino.model.Medicamento
import com.example.gorraselpadrino.viewmodel.CartViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CartViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel

    @Before
    fun setup() {
        viewModel = CartViewModel()
    }

    @Test
    fun `agregar medicamento al carrito aumenta la lista`() {
        val medicamento = Medicamento(
            id = "1",
            nombre = "Paracetamol",
            descripcion = "Analgesico",
            precio = 10.0,
            stock = 100,
            imagenUrl = "http://example.com/img.png"
        )

        viewModel.addMedicamentoAlCarrito(medicamento)

        assertEquals(1, viewModel.medicamentosEnCarrito.size)
        assertEquals(medicamento, viewModel.medicamentosEnCarrito[0])
    }

    @Test
    fun `remover medicamento del carrito disminuye la lista`() {
        val medicamento = Medicamento(
            id = "1",
            nombre = "Paracetamol",
            descripcion = "Analgesico",
            precio = 10.0,
            stock = 100,
            imagenUrl = "http://example.com/img.png"
        )
        viewModel.addMedicamentoAlCarrito(medicamento)
        viewModel.removeMedicamentoDelCarrito(medicamento)

        assertTrue(viewModel.medicamentosEnCarrito.isEmpty())
    }

    @Test
    fun `calcular total suma correctamente los precios`() {
        val med1 = Medicamento(
            id = "1", nombre = "A", descripcion = "", precio = 10.0,
            stock = 1, imagenUrl = ""
        )
        val med2 = Medicamento(
            id = "2", nombre = "B", descripcion = "", precio = 20.5,
            stock = 1, imagenUrl = ""
        )

        viewModel.addMedicamentoAlCarrito(med1)
        viewModel.addMedicamentoAlCarrito(med2)

        val total = viewModel.calcularTotal()

        assertEquals(30.5, total, 0.001)
    }

    @Test
    fun `limpiar carrito deja la lista vacia`() {
        val medicamento = Medicamento(
            id = "1",
            nombre = "Paracetamol",
            descripcion = "Analgesico",
            precio = 10.0,
            stock = 100,
            imagenUrl = "http://example.com/img.png"
        )
        viewModel.addMedicamentoAlCarrito(medicamento)
        viewModel.limpiarCarrito()

        assertTrue(viewModel.medicamentosEnCarrito.isEmpty())
    }
}
