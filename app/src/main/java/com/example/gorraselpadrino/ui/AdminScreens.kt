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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// --- 7. Gestión de Pedidos ---
@Composable
fun AdminOrdersScreen(navController: NavController) {
    val pedidosSimulados = listOf(
        "Pedido #1001 - Pendiente - $15.50",
        "Pedido #1002 - Completado - $42.00",
        "Pedido #1003 - Cancelado - $10.00"
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Volver") }
            Text("Gestión de Pedidos", style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(pedidosSimulados) { pedido ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Text(pedido, modifier = Modifier.padding(16.dp))
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
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(categoria)
                        Text("Editar", color = Color.Blue, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
