package com.example.gorraselpadrino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gorraselpadrino.ui.theme.GorrasElPadrinoTheme
import com.example.gorraselpadrino.viewmodel.UserAdminViewModel
import com.example.gorraselpadrino.ui.GorrasElPadrinoNavegacion

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GorrasElPadrinoTheme {
                val userAdminViewModel: UserAdminViewModel = viewModel()
                GorrasElPadrinoNavegacion(userAdminViewModel = userAdminViewModel)
            }
        }
    }
}