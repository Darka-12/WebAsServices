package com.example.psiconova

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    // 1. Estados para el proceso de Login y Registro tradiccionales
    var isLoading = mutableStateOf(false)
    var authError = mutableStateOf<String?>(null)

    // 2. Estados reactivos (StateFlow) para la Pantalla de Perfil
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    private val _isProfileLoading = MutableStateFlow(false)
    val isProfileLoading: StateFlow<Boolean> = _isProfileLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    var usuarioLogueadoEmail: String = ""

    // --- MÉTODOS DE AUTENTICACIÓN ---
    fun loginUsuario(email: String, contrasena: String, onLoginSuccess: (String) -> Unit) {
        if (email.isBlank() || contrasena.isBlank()) {
            authError.value = "Por favor, llena todos los campos."
            return
        }

        isLoading.value = true
        authError.value = null

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.iniciarSesion(email, contrasena)
                isLoading.value = false

                if (response.status == "success" && response.rol != null) {
                    onLoginSuccess(response.rol)
                } else {
                    authError.value = response.message ?: "Credenciales incorrectas"
                }
            } catch (e: Exception) {
                isLoading.value = false
                authError.value = "Error de red: No se pudo conectar con el servidor XAMPP"
            }
        }
    }

    fun registrarUsuario(
        nombre: String,
        email: String,
        contrasena: String,
        rol: String,
        cedula: String = "",
        especialidad: String = "",
        precio: String = "",
        onRegistroSuccess: () -> Unit
    ) {
        if (nombre.isBlank() || email.isBlank() || contrasena.isBlank()) {
            authError.value = "Por favor llena los campos obligatorios."
            return
        }

        isLoading.value = true
        authError.value = null

        viewModelScope.launch {
            try {
                val precioDouble = precio.toDoubleOrNull()
                val response = RetrofitClient.apiService.registrarUsuario(
                    nombre = nombre,
                    email = email,
                    password = contrasena,
                    rol = rol,
                    cedula = cedula.ifBlank { null },
                    especialidad = especialidad.ifBlank { null },
                    precio = precioDouble
                )

                isLoading.value = false

                if (response.status == "success") {
                    onRegistroSuccess()
                } else {
                    authError.value = response.message ?: "Error al registrar"
                }
            } catch (e: Exception) {
                isLoading.value = false
                authError.value = "Error de red al registrar usuario"
            }
        }
    }

    fun autenticarConGoogle(idToken: String, onLoginSuccess: (String) -> Unit) {
        isLoading.value = true
        authError.value = null

        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.loginConGoogle(idToken)
                isLoading.value = false

                if (response.status == "success" && response.usuario != null) {
                    onLoginSuccess(response.usuario.rol)
                } else {
                    authError.value = response.message ?: "Error al autenticar con Google"
                }
            } catch (e: Exception) {
                isLoading.value = false
                authError.value = "Error de red al conectar con Google"
            }
        }
    }

    // --- MÉTODOS DEL PERFIL ---
    fun cargarPerfil(email: String) {
        viewModelScope.launch {
            _isProfileLoading.value = true
            _errorMessage.value = null
            try {
                val response = RetrofitClient.apiService.obtenerPerfil(email)
                if (response.status == "success" && response.user != null) {
                    _userProfile.value = response.user
                } else {
                    _errorMessage.value = response.message ?: "No se pudo cargar el perfil"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error al conectar con el servidor: ${e.message}"
            } finally {
                _isProfileLoading.value = false
            }
        }
    }
    fun cerrarSesion() {
        viewModelScope.launch {
            _userProfile.value = null
            authError.value = null
            // Aquí puedes borrar las preferencias en DataStore si lo implementas
        }
    }
}