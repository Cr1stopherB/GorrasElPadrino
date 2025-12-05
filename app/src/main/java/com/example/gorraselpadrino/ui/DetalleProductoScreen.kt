package com.example.gorraselpadrino.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gorraselpadrino.viewmodel.CartViewModel
import com.example.gorraselpadrino.viewmodel.CatalogoViewModel

@Composable
fun DetalleProductoScreen(
    navController: NavController,
    medicamentoId: String?,
    catalogoViewModel: CatalogoViewModel,
    cartViewModel: CartViewModel
) {
    val medicamento = catalogoViewModel.medicamentos.find { it.id == medicamentoId }

    if (medicamento != null) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            AsyncImage(
                model = medicamento.imagenUrl,
                contentDescription = medicamento.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(16.dp))
            Text(medicamento.nombre, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            Text("Laboratorio: ${medicamento.laboratorio}", style = MaterialTheme.typography.bodyLarge)
            Text("Categoría: ${medicamento.categoria}", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text(medicamento.descripcion, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(16.dp))
            Text("Precio: $${medicamento.precio}", style = MaterialTheme.typography.titleLarge)
            
            if (medicamento.requiereReceta) {
                 Text("Requiere receta médica", color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { 
                    cartViewModel.addMedicamentoAlCarrito(medicamento)
                    navController.navigate("carrito") 
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar al carrito")
            }
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                Text("Producto no encontrado")
                Spacer(Modifier.height(16.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Volver")
                }
            }
        }
    }
}
