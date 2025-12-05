package com.example.gorraselpadrino.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gorraselpadrino.viewmodel.UserAdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    userAdminViewModel: UserAdminViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("eFarmaPlus") },
                actions = {
                    IconButton(onClick = { navController.navigate("carrito") }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Banner Promocional Simulado
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp), // Reducido un poco la altura para mejor proporción
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    // Texto del banner más pequeño
                    Text(
                        "¡Ofertas Especiales en Vitaminas!",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Accesos Rápidos (Categorías)
            Text("Categorías", style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CategoryButton("Medicamentos") { navController.navigate("catalogo") }
                // Ahora "Cuidado" y "Bebés" también navegan al catálogo
                CategoryButton("Cuidado") { navController.navigate("catalogo") }
                CategoryButton("Bebés") { navController.navigate("catalogo") }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Menú Principal
            Button(onClick = { navController.navigate("catalogo") }, modifier = Modifier.fillMaxWidth()) {
                Text("Ver todo el catálogo")
            }
            
            Spacer(modifier = Modifier.height(8.dp))

            if (userAdminViewModel.isAdmin) {
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                Text("Administración", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { navController.navigate("admin") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Panel de Administrador")
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))

            // Footer
            TextButton(onClick = { navController.navigate("info") }) {
                Text("Quiénes somos")
            }
            
            Button(
                onClick = {
                    userAdminViewModel.isAdmin = false
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}

@Composable
fun CategoryButton(text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(8.dp), contentAlignment = Alignment.Center) {
            // Texto de categoría más pequeño para ajustar
            Text(
                text,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
