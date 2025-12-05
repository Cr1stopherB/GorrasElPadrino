package com.example.gorraselpadrino.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gorraselpadrino.viewmodel.CatalogoViewModel

@Composable
fun HomeAdminScreen(
    navController: NavController,
    catalogViewModel: CatalogoViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Panel de Administrador", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Resumen del Negocio", style = MaterialTheme.typography.titleMedium)
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Ventas Hoy: $150.00")
                Text("Pedidos Pendientes: 5")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("catalogoAdmin") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gestionar Productos")
        }
        
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("adminOrders") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gestionar Pedidos")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("adminUsers") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gestionar Usuarios")
        }
        
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("adminCategories") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gestionar Categorías")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate("home") {
                    popUpTo(0) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Inicio (Modo Usuario)")
        }
    }
}
