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

        Button(
            onClick = { navController.navigate("catalogoAdmin") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gestionar Catálogo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("home") {
                    popUpTo(0) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Inicio")
        }
    }
}