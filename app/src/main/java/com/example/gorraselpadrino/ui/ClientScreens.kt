package com.example.gorraselpadrino.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import com.example.gorraselpadrino.viewmodel.CartViewModel

// --- 1. Registro ---
@Composable
fun RegisterScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear Cuenta", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre completo") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo electrónico") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = password, 
            onValueChange = { password = it }, 
            label = { Text("Contraseña") }, 
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = confirmPassword, 
            onValueChange = { confirmPassword = it }, 
            label = { Text("Confirmar contraseña") }, 
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(24.dp))
        Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
            Text("Registrarse")
        }
        TextButton(onClick = { navController.popBackStack() }) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}

// --- 1. Recuperar Contraseña ---
@Composable
fun RecoverPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Recuperar Contraseña", style = MaterialTheme.typography.headlineMedium)
        Text("Ingresa tu correo y te enviaremos instrucciones.", textAlign = TextAlign.Center, modifier = Modifier.padding(vertical = 16.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo electrónico") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(24.dp))
        Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
            Text("Enviar correo")
        }
        TextButton(onClick = { navController.popBackStack() }) {
            Text("Volver al login")
        }
    }
}

// --- 6. Checkout ---
@Composable
fun CheckoutScreen(navController: NavController, cartViewModel: CartViewModel) {
    var direccion by remember { mutableStateOf("") }
    var tarjeta by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState())) {
        Text("Finalizar Compra", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))
        
        Text("Dirección de envío", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(value = direccion, onValueChange = { direccion = it }, label = { Text("Dirección completa") }, modifier = Modifier.fillMaxWidth())
        
        Spacer(Modifier.height(24.dp))
        Text("Método de Pago", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(value = tarjeta, onValueChange = { tarjeta = it }, label = { Text("Número de tarjeta") }, modifier = Modifier.fillMaxWidth())
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(value = fecha, onValueChange = { fecha = it }, label = { Text("MM/AA") }, modifier = Modifier.weight(1f).padding(end = 8.dp))
            OutlinedTextField(value = cvv, onValueChange = { cvv = it }, label = { Text("CVV") }, modifier = Modifier.weight(1f).padding(start = 8.dp))
        }
        
        Spacer(Modifier.height(24.dp))
        Text("Resumen", style = MaterialTheme.typography.titleMedium)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total a pagar:")
            Text("$${cartViewModel.calcularTotal()}", fontWeight = FontWeight.Bold)
        }
        
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = { 
                cartViewModel.limpiarCarrito()
                navController.navigate("confirmation") 
            }, 
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pagar y Finalizar")
        }
    }
}

// --- 7. Confirmación ---
@Composable
fun OrderConfirmationScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.CheckCircle, contentDescription = "Éxito", tint = Color(0xFF4CAF50), modifier = Modifier.size(100.dp))
        Spacer(Modifier.height(24.dp))
        Text("¡Pedido Exitoso!", style = MaterialTheme.typography.headlineMedium)
        Text("Tu pedido #12345 ha sido procesado.", textAlign = TextAlign.Center)
        Spacer(Modifier.height(32.dp))
        Button(onClick = { navController.navigate("home") { popUpTo(0) } }, modifier = Modifier.fillMaxWidth()) {
            Text("Volver al Inicio")
        }
    }
}

// --- 8. Perfil ---
@Composable
fun ProfileScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Volver") }
            Text("Mi Perfil", style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.height(24.dp))
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Juan Pérez", style = MaterialTheme.typography.titleMedium)
                Text("cliente@email.com", style = MaterialTheme.typography.bodyMedium)
            }
        }
        
        Spacer(Modifier.height(24.dp))
        Text("Mis Direcciones", style = MaterialTheme.typography.titleMedium)
        Card(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text("Calle Falsa 123, Ciudad", modifier = Modifier.padding(16.dp))
        }
        
        Spacer(Modifier.height(24.dp))
        Text("Métodos de Pago", style = MaterialTheme.typography.titleMedium)
        Card(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text("Visa terminada en 4242", modifier = Modifier.padding(16.dp))
        }
    }
}

// --- 9. Configuración ---
@Composable
fun SettingsScreen(navController: NavController) {
    var notificaciones by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Volver") }
            Text("Configuración", style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.height(24.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Recibir notificaciones")
            Switch(checked = notificaciones, onCheckedChange = { notificaciones = it })
        }
        
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        
        Button(
            onClick = { navController.navigate("login") { popUpTo(0) } },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar Sesión")
        }
    }
}
