package com.example.psiconova

data class Psicologo(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val precioSesion: Double,
    val calificacion: Double,
    val distanciaKm: Double,
    val fotoUrl: String? = null
)