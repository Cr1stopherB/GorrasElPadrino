package com.example.gorraselpadrino.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gorraselpadrino.viewmodel.UserAdminViewModel

@Composable
fun LoginScreen(navController: NavController, userAdminViewModel: UserAdminViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    fun intentarLogin() {
        showError = false
        if (username == "admin" && password == "admin123") {
            userAdminViewModel.isAdmin = true
            navController.navigate("admin") {
                popUpTo("login") { inclusive = true }
            }
        } else if (username == "cliente" && password == "cliente123") {
            userAdminViewModel.isAdmin = false
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        } else {
            showError = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { intentarLogin() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }

        if (showError) {
            Spacer(modifier = Modifier.height(10.dp))
            Text("Usuario o contraseña incorrectos", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("register") }) {
            Text("¿No tienes cuenta? Regístrate")
        }

        TextButton(onClick = { navController.navigate("recoverPassword") }) {
            Text("Olvidé mi contraseña")
        }
    }
}
