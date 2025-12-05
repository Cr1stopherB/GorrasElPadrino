package com.example.gorraselpadrino.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.gorraselpadrino.viewmodel.CartViewModel
import com.example.gorraselpadrino.viewmodel.CatalogoViewModel
import com.example.gorraselpadrino.viewmodel.UserAdminViewModel

/** Función de navegación principal de la aplicación Define todas las rutas y pantallas de la app */
@Composable
fun GorrasElPadrinoNavegacion(userAdminViewModel: UserAdminViewModel) {
    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()
    val catalogoViewModel: CatalogoViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {
        // Login & Auth
        composable("login") { LoginScreen(navController, userAdminViewModel) }
        composable("register") { RegisterScreen(navController) }
        composable("recoverPassword") { RecoverPasswordScreen(navController) }

        // Cliente
        composable("home") { HomeScreen(navController, userAdminViewModel) }
        composable("catalogo") {
            CatalogoScreen(
                    viewModel = catalogoViewModel,
                    onMedicamentoClick = { medicamento ->
                        navController.navigate("detalle/${medicamento.id}")
                    },
                    navController = navController
            )
        }
        composable(
            "detalle/{medicamentoId}",
            arguments = listOf(navArgument("medicamentoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val medicamentoId = backStackEntry.arguments?.getString("medicamentoId")
            DetalleProductoScreen(navController, medicamentoId, catalogoViewModel, cartViewModel)
        }
        composable("carrito") { CarritoScreen(navController, cartViewModel) }
        composable("checkout") { CheckoutScreen(navController, cartViewModel) }
        composable("confirmation") { OrderConfirmationScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("info") { InfoScreen(navController) }

        // Admin
        composable("admin") {
            HomeAdminScreen(navController = navController, catalogViewModel = catalogoViewModel)
        }
        composable("catalogoAdmin") {
            CatalogoAdminScreen(navController = navController, catalogViewModel = catalogoViewModel)
        }
        composable("adminOrders") { AdminOrdersScreen(navController) }
        composable("adminUsers") { AdminUsersScreen(navController) }
        composable("adminCategories") { AdminCategoriesScreen(navController) }
    }
}
