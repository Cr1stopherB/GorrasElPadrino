package com.example.gorraselpadrino.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gorraselpadrino.model.Medicamento
import com.example.gorraselpadrino.viewmodel.CatalogoViewModel

/**
 * Pantalla principal del catálogo de medicamentos Muestra una grilla de medicamentos obtenidos de
 * la API
 */
@Composable
fun CatalogoScreen(
    viewModel: CatalogoViewModel,
    onMedicamentoClick: (Medicamento) -> Unit,
    navController: NavController? = null // Hacemos el navController opcional por compatibilidad
) {
    val medicamentos = viewModel.medicamentos
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Barra superior simple con botón de volver
        if (navController != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Volver")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "Catálogo de Medicamentos",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Divider()
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Text("Cargando medicamentos...", modifier = Modifier.padding(top = 16.dp))
                    }
                }
                errorMessage != null -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(onClick = { viewModel.loadMedicamentos() }) { Text("Reintentar") }
                        if (navController != null) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { navController.popBackStack() }) { Text("Volver") }
                        }
                    }
                }
                medicamentos.isEmpty() -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("No hay medicamentos disponibles")
                        Button(onClick = { viewModel.loadMedicamentos() }) { Text("Recargar") }
                        if (navController != null) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { navController.popBackStack() }) { Text("Volver") }
                        }
                    }
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(medicamentos) { medicamento ->
                            MedicamentoCard(
                                medicamento = medicamento,
                                onClick = { onMedicamentoClick(medicamento) }
                            )
                        }
                    }
                }
            }
        }
    }
}

/** Tarjeta individual para mostrar un medicamento */
@Composable
fun MedicamentoCard(medicamento: Medicamento, onClick: () -> Unit) {
    Card(modifier = Modifier.padding(4.dp).clickable { onClick() }) {
        Column {
            // Imagen del medicamento
            AsyncImage(
                model = medicamento.imagenUrl,
                contentDescription = medicamento.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                contentScale = ContentScale.Crop,
                // Placeholder en caso de carga o error
                // placeholder = painterResource(R.drawable.ic_placeholder), // Opcional
                // error = painterResource(R.drawable.ic_error) // Opcional
            )

            // Info del medicamento
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = medicamento.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$${medicamento.precio}",
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                // Indicador si requiere receta
                if (medicamento.requiereReceta) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Requiere receta",
                        color = Color.Red,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .background(
                                Color.Red.copy(alpha = 0.1f),
                                RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}
