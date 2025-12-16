package com.example.gorraselpadrino.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gorraselpadrino.viewmodel.AdminOrdersViewModel

// --- 7. Gestión de Pedidos ---
@Composable
fun AdminOrdersScreen(
    navController: NavController, 
    viewModel: AdminOrdersViewModel
) {
    val pedidos = viewModel.pedidos
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Volver") }
            Text("Pedidos de Clientes", style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.height(16.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if (errorMessage != null) {
                Text(
                    "Error al cargar pedidos: $errorMessage", 
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(Modifier.height(8.dp))
                Button(onClick = { viewModel.loadPedidos() }) {
                    Text("Reintentar")
                }
            } else if (pedidos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay pedidos registrados.")
                }
            } else {
                LazyColumn {
                    items(pedidos) { pedido ->
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Pedido #${pedido.id}", style = MaterialTheme.typography.titleMedium)
                                    Text("$${pedido.total}", style = MaterialTheme.typography.titleMedium)
                                }
                                Spacer(Modifier.height(4.dp))
                                Text("Cliente: ${pedido.cliente}")
                                Text("Fecha: ${pedido.fecha}")
                                Text(
                                    "Estado: ${pedido.estado}", 
                                    color = when(pedido.estado) {
                                        "Completado" -> Color.Green
                                        "Pendiente" -> Color(0xFFFFA500) // Orange
                                        "Cancelado" -> Color.Red
                                        else -> Color.Gray
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- 9. Gestión de Usuarios ---
@Composable
fun AdminUsersScreen(navController: NavController) {
    val usuariosSimulados = listOf(
        "Juan Pérez - Activo",
        "María García - Activo",
        "Carlos López - Bloqueado"
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Volver") }
            Text("Gestión de Usuarios", style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(usuariosSimulados) { usuario ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Text(usuario, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

// --- 5. Gestión de Categorías ---
@Composable
fun AdminCategoriesScreen(navController: NavController) {
    // Esta pantalla se mantiene pero el acceso se quitó del HomeAdminScreen
    val categoriasSimuladas = listOf("Medicamentos", "Cuidado Personal", "Vitaminas", "Bebés")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Volver") }
            Text("Gestión de Categorías", style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.height(16.dp))

        Button(onClick = { /* Lógica para agregar */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Agregar Nueva Categoría")
        }
        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(categoriasSimuladas) { categoria ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Text(categoria, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}
