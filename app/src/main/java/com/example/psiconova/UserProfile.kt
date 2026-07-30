package com.example.psiconova

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("rol") val rol: String,
    @SerializedName("cedula") val cedula: String? = null,
    @SerializedName("especialidad") val especialidad: String? = null,
    @SerializedName("precio") val precio: Double? = null,
    @SerializedName("foto_url") val fotoUrl: String? = null
)

// Modelo para mapear la respuesta del servidor PHP
data class ProfileResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String?,
    @SerializedName("user") val user: UserProfile?
)

data class SolicitudPsicologo(
    val idUsuario: Int,
    val nombre: String,
    val email: String,
    val cedula: String,
    val especialidad: String,
    val precioSesion: Double
)

data class CitaItem(
    val id: Int,
    val nombrePaciente: String,
    val fechaHora: String,
    val motivo: String,
    val estado: String // "pendiente" o "confirmada"
)