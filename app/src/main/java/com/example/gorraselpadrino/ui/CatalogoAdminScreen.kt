package com.example.gorraselpadrino.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.gorraselpadrino.viewmodel.CatalogoViewModel
import com.example.gorraselpadrino.model.Gorra
import com.example.gorraselpadrino.model.CategoriaGorra

@Composable
fun CatalogoAdminScreen(
    navController: NavController,
    catalogViewModel: CatalogoViewModel
) {
    var resultado by remember { mutableStateOf("") }
    var showEditDialog by remember { mutableStateOf(false) }
    var editGorra by remember { mutableStateOf<Gorra?>(null) }
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf(CategoriaGorra.CASUAL) }
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    fun limpiarCampos() {
        nombre = ""
        precio = ""
        descripcion = ""
        categoria = CategoriaGorra.CASUAL
        editGorra = null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Catálogo de gorras (ADMIN)", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        catalogViewModel.gorras.forEach { gorra ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("${gorra.nombre} (ID: ${gorra.id})", style = MaterialTheme.typography.bodyLarge)
                    Text("Precio: ${gorra.precio} | ${gorra.categoria.name}", style = MaterialTheme.typography.bodySmall)
                    Text(gorra.descripcion, style = MaterialTheme.typography.bodySmall)
                }
                Column(
                    modifier = Modifier.widthIn(min = 120.dp).fillMaxHeight(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            editGorra = gorra
                            nombre = gorra.nombre
                            precio = gorra.precio.toString()
                            descripcion = gorra.descripcion
                            categoria = gorra.categoria
                            showEditDialog = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Modificar") }
                    Button(
                        onClick = {
                            catalogViewModel.removeGorra(gorra)
                            resultado = "Gorra eliminada"
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Eliminar") }
                }
            }
            Divider()
        }

        if (resultado.isNotEmpty()) {
            Spacer(Modifier.height(10.dp))
            Text(resultado, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { navController.navigate("admin") { popUpTo(0) { inclusive = true } } },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Volver") }
    }

    if (showEditDialog && editGorra != null) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false; limpiarCampos() },
            confirmButton = {
                Button(onClick = {
                    val nombreValido = nombre.isNotBlank()
                    val descripcionValida = descripcion.isNotBlank()
                    val precioValido = precio.toDoubleOrNull()?.let { it > 0 } ?: false
                    val existe = catalogViewModel.gorras.any {
                        it.id != editGorra!!.id && it.nombre.equals(nombre, ignoreCase = true)
                    }
                    if (!nombreValido || !descripcionValida || !precioValido) {
                        resultado = "Campos obligatorios faltantes o inválidos"
                    } else if (existe) {
                        resultado = "Ya existe otra gorra con ese nombre"
                    } else {
                        catalogViewModel.updateGorra(
                            Gorra(editGorra!!.id, nombre, precio.toDouble(), descripcion, categoria)
                        )
                        resultado = "Gorra modificada"
                        showEditDialog = false
                        limpiarCampos()
                    }
                }) { Text("Guardar") }
            },
            dismissButton = {
                Button(onClick = { showEditDialog = false; limpiarCampos() }) { Text("Cancelar") }
            },
            title = { Text("Modificar gorra") },
            text = {
                Column {
                    OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = precio,
                        onValueChange = { precio = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("Precio") }
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
                    Spacer(Modifier.height(8.dp))
                    Box {
                        OutlinedButton(onClick = { expanded = true }) { Text(categoria.name) }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            CategoriaGorra.values().forEach { cat ->
                                DropdownMenuItem(
                                    onClick = { categoria = cat; expanded = false },
                                    text = { Text(cat.name) }
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}