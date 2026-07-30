package com.example.psiconova

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.psiconova.ui.theme.PsicoNovaTheme

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PsicoNovaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(authViewModel = authViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    // Observar estados desde el AuthViewModel para la pantalla de Perfil
    val userProfile by authViewModel.userProfile.collectAsState()
    val isProfileLoading by authViewModel.isProfileLoading.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // 1. Pantalla de Login
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = { rol ->
                    when (rol) {
                        "admin" -> navController.navigate("home_admin") { popUpTo("login") { inclusive = true } }
                        "psicologo" -> navController.navigate("home_psicologo") { popUpTo("login") { inclusive = true } }
                        else -> navController.navigate("home_cliente") { popUpTo("login") { inclusive = true } }
                    }
                },
                onNavigateToRegistro = {
                    navController.navigate("registro")
                }
            )
        }

        // 2. Pantalla de Registro
        composable("registro") {
            RegistroScreen(
                viewModel = authViewModel,
                onRegistroExitoso = {
                    navController.popBackStack()
                },
                onVolverALogin = {
                    navController.popBackStack()
                }
            )
        }

        // 3. Menús Principales por Rol
        composable("home_cliente") {
            HomeClienteScreen(
                onPsicologoClick = { idPsicologo -> },
                onNavigateToProfile = {
                    authViewModel.cargarPerfil(email = "andres@gmail.com")
                    navController.navigate("profile")
                }
            )
        }

        composable("home_admin") {
            HomeAdminScreen(
                onNavigateToProfile = {
                    authViewModel.cargarPerfil(email = "admin@psiconova.com")
                    navController.navigate("profile")
                }
            )
        }

        composable("home_psicologo") {
            HomePsicologoScreen(
                onNavigateToProfile = {
                    authViewModel.cargarPerfil(email = "jose@gmail.com")
                    navController.navigate("profile")
                }
            )
        }

        // 4. Pantalla de Perfil de Usuario
        composable("profile") {
            ProfileScreen(
                userProfile = userProfile,
                isLoading = isProfileLoading,
                errorMessage = errorMessage,
                onBackClick = {
                    navController.popBackStack()
                },
                onLogoutClick = {
                    authViewModel.cerrarSesion()
                    navController.navigate("login") {
                        popUpTo(id = 0) { inclusive = true }
                    }
                }
            )
        }
    }
}