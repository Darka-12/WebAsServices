package com.example.psiconova

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAdminScreen(onNavigateToProfile: () -> Unit = {}) {
    val solicitudesPendientes = remember {
        mutableStateListOf(
            SolicitudPsicologo(
                idUsuario = 5,
                nombre = "Dr. Roberto Silva",
                email = "roberto.silva@email.com",
                cedula = "CED-9842105",
                especialidad = "Psicología Infantil y Adolescentes",
                precioSesion = 450.0
            ),
            SolicitudPsicologo(
                idUsuario = 8,
                nombre = "Mtra. Carmen López",
                email = "carmen.lopez@email.com",
                cedula = "CED-4512093",
                especialidad = "Terapia Cognitivo-Conductual",
                precioSesion = 600.0
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Sistema de Gestión",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                        Text(
                            text = "Panel Administrador",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Ir a mi perfil",
                            tint = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Pendientes de Verificación", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                text = "${solicitudesPendientes.size} Psicólogos",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Solicitudes por Aprobar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            if (solicitudesPendientes.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay solicitudes pendientes de aprobación",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                items(solicitudesPendientes) { solicitud ->
                    TarjetaSolicitudPsicologo(
                        solicitud = solicitud,
                        onAprobar = { solicitudesPendientes.remove(solicitud) },
                        onRechazar = { solicitudesPendientes.remove(solicitud) }
                    )
                }
            }
        }
    }
}