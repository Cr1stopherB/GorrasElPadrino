package com.example.gorraselpadrino.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gorraselpadrino.model.Medicamento
import com.example.gorraselpadrino.viewmodel.CartViewModel

@Composable
fun CarritoScreen(navController: NavController, cartViewModel: CartViewModel) {
    val cartItems = cartViewModel.medicamentosEnCarrito

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Carrito de compras", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        
        if (cartItems.isNotEmpty()) {
            cartItems.forEach { medicamento ->
                Card(
                    Modifier
                        .padding(vertical = 6.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(medicamento.nombre, fontWeight = FontWeight.Bold)
                            Text("ID: ${medicamento.id}", style = MaterialTheme.typography.bodySmall)
                        }
                        Text("$${medicamento.precio}", fontWeight = FontWeight.Bold)
                        IconButton(onClick = { cartViewModel.removeMedicamentoDelCarrito(medicamento) }) {
                            Text("X", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
            
            Spacer(Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total:", style = MaterialTheme.typography.titleLarge)
                Text("$${cartViewModel.calcularTotal()}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            
            Spacer(Modifier.height(24.dp))
            
            Button(
                onClick = { navController.navigate("checkout") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Proceder al Pago")
            }
            
            Spacer(Modifier.height(10.dp))
            
            TextButton(
                onClick = { cartViewModel.limpiarCarrito() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Vaciar carrito", color = MaterialTheme.colorScheme.error)
            }
        } else {
            Box(modifier = Modifier.fillMaxSize().padding(top = 50.dp), contentAlignment = Alignment.Center) {
                Text("Tu carrito está vacío.", style = MaterialTheme.typography.bodyLarge)
            }
        }
        
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seguir Comprando")
        }
    }
}
