package com.example.gorraselpadrino.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Alignment

@Composable
fun InfoScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Quiénes Somos", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))
        Text(
            "Gorras El Padrino es tu tienda especializada en gorras de calidad. Ofrecemos una amplia variedad de estilos deportivos, casuales y elegantes para todos los gustos.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(18.dp))
        Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }
    }
}