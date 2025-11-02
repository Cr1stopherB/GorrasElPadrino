package com.example.gorraselpadrino.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.gorraselpadrino.viewmodel.CatalogoViewModel
import com.example.gorraselpadrino.model.Gorra
import com.example.gorraselpadrino.viewmodel.UserAdminViewModel

@Composable
fun CatalogoScreen(
    navController: NavController,
    catalogoViewModel: CatalogoViewModel,
    onAgregarAlCarrito: (Gorra) -> Unit = {},
    userAdminViewModel: UserAdminViewModel
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Catálogo de gorras", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        catalogoViewModel.gorras.forEach { gorra ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(gorra.nombre, style = MaterialTheme.typography.bodyLarge)
                    Text("Precio: ${gorra.precio} | ${gorra.categoria.name}", style = MaterialTheme.typography.bodySmall)
                    Text(gorra.descripcion, style = MaterialTheme.typography.bodySmall)
                }
                Button(
                    onClick = { onAgregarAlCarrito(gorra) },
                    modifier = Modifier.padding(start = 4.dp)
                ) { Text("Agregar") }
            }
            Divider()
        }
        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier.padding(top = 12.dp)
        ) { Text("Volver") }
    }
}