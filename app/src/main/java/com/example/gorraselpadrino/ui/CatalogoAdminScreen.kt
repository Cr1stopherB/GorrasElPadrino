package com.example.gorraselpadrino.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
 * Pantalla de administración del catálogo de medicamentos.
 * Muestra los productos en un Grid similar al catálogo de usuarios,
 * pero con opciones para editar y eliminar.
 */
@Composable
fun CatalogoAdminScreen(navController: NavController, catalogViewModel: CatalogoViewModel) {
    var showEditDialog by remember { mutableStateOf(false) }
    var editMedicamento by remember { mutableStateOf<Medicamento?>(null) }
    var isNewProduct by remember { mutableStateOf(false) }

    // Estados para el formulario de edición
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var laboratorio by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var requiereReceta by remember { mutableStateOf(false) }
    
    var resultado by remember { mutableStateOf("") }

    // Funciones auxiliares para el diálogo
    fun openEdit(medicamento: Medicamento) {
        editMedicamento = medicamento
        isNewProduct = false
        nombre = medicamento.nombre
        precio = medicamento.precio.toString()
        descripcion = medicamento.descripcion
        laboratorio = medicamento.laboratorio
        categoria = medicamento.categoria
        stock = medicamento.stock.toString()
        requiereReceta = medicamento.requiereReceta
        showEditDialog = true
        resultado = ""
    }

    fun openNew() {
        editMedicamento = null
        isNewProduct = true
        nombre = ""
        precio = ""
        descripcion = ""
        laboratorio = ""
        categoria = ""
        stock = ""
        requiereReceta = false
        showEditDialog = true
        resultado = ""
    }

    fun closeEdit() {
        showEditDialog = false
        editMedicamento = null
        resultado = ""
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { openNew() }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Barra superior
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Volver")
                }
                Text(
                    "Gestionar Catálogo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(48.dp)) // Espaciador para equilibrar el título
            }

            Divider()

            if (resultado.isNotEmpty()) {
                Text(
                    text = resultado,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            if (catalogViewModel.medicamentos.isEmpty()) {
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                     Column(horizontalAlignment = Alignment.CenterHorizontally) {
                         Text("No hay productos cargados.")
                         Button(onClick = { catalogViewModel.loadMedicamentos() }) {
                             Text("Recargar")
                         }
                     }
                 }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    contentPadding = PaddingValues(bottom = 80.dp) // Espacio para el FAB
                ) {
                    items(catalogViewModel.medicamentos) { medicamento ->
                        AdminMedicamentoCard(
                            medicamento = medicamento,
                            onEditClick = { openEdit(medicamento) },
                            onDeleteClick = { 
                                catalogViewModel.removeMedicamento(medicamento)
                                resultado = "Producto eliminado correctamente"
                            }
                        )
                    }
                }
            }
        }
    }

    // Diálogo de Edición / Creación
    if (showEditDialog) {
         AlertDialog(
            onDismissRequest = { closeEdit() },
            title = { Text(if (isNewProduct) "Nuevo Producto" else "Editar Producto") },
            text = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                ) {
                     OutlinedTextField(
                         value = nombre, 
                         onValueChange = { nombre = it }, 
                         label = { Text("Nombre") },
                         modifier = Modifier.fillMaxWidth()
                     )
                     Spacer(Modifier.height(8.dp))
                     OutlinedTextField(
                         value = precio, 
                         onValueChange = { precio = it }, 
                         label = { Text("Precio") },
                         modifier = Modifier.fillMaxWidth()
                     )
                     Spacer(Modifier.height(8.dp))
                     OutlinedTextField(
                         value = stock, 
                         onValueChange = { stock = it }, 
                         label = { Text("Stock") },
                         modifier = Modifier.fillMaxWidth()
                     )
                     Spacer(Modifier.height(8.dp))
                     OutlinedTextField(
                         value = descripcion, 
                         onValueChange = { descripcion = it }, 
                         label = { Text("Descripción") },
                         modifier = Modifier.fillMaxWidth()
                     )
                     Spacer(Modifier.height(8.dp))
                     OutlinedTextField(
                         value = laboratorio, 
                         onValueChange = { laboratorio = it }, 
                         label = { Text("Laboratorio") },
                         modifier = Modifier.fillMaxWidth()
                     )
                     Spacer(Modifier.height(8.dp))
                     OutlinedTextField(
                         value = categoria, 
                         onValueChange = { categoria = it }, 
                         label = { Text("Categoría") },
                         modifier = Modifier.fillMaxWidth()
                     )
                     Spacer(Modifier.height(8.dp))
                     Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = requiereReceta, onCheckedChange = { requiereReceta = it })
                        Text("Requiere Receta")
                     }
                }
            },
            confirmButton = {
                Button(onClick = {
                     val precioDouble = precio.toDoubleOrNull() ?: 0.0
                     val stockInt = stock.toIntOrNull() ?: 0

                     if (isNewProduct) {
                         // Crear nuevo
                         val nuevo = Medicamento(
                             id = "", // El ID lo generará la BD/API, pero el modelo local necesita uno temporal o vacio que se ignora al crear
                             nombre = nombre,
                             descripcion = descripcion,
                             precio = precioDouble,
                             stock = stockInt,
                             imagenUrl = "", // TODO: Manejo de imagen
                             categoria = categoria,
                             laboratorio = laboratorio,
                             requiereReceta = requiereReceta
                         )
                         catalogViewModel.addMedicamento(nuevo)
                         resultado = "Producto creado correctamente"
                     } else {
                         // Actualizar existente
                         val item = editMedicamento!!
                         val nuevo = item.copy(
                            nombre = nombre,
                            precio = precioDouble,
                            stock = stockInt,
                            descripcion = descripcion,
                            laboratorio = laboratorio,
                            categoria = categoria,
                            requiereReceta = requiereReceta
                         )
                         catalogViewModel.updateMedicamento(nuevo)
                         resultado = "Producto actualizado correctamente"
                     }
                     
                     closeEdit()
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                Button(onClick = { closeEdit() }) { Text("Cancelar") }
            }
         )
    }
}

@Composable
fun AdminMedicamentoCard(
    medicamento: Medicamento,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Imagen
             AsyncImage(
                model = medicamento.imagenUrl,
                contentDescription = medicamento.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                contentScale = ContentScale.Crop
            )
            
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = medicamento.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = "$${medicamento.precio}",
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Botones de acción
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
