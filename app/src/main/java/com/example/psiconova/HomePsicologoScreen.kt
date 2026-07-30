package com.example.psiconova

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.mutableStateListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePsicologoScreen(
    aprobadoPorAdmin: Boolean = true,
    onNavigateToProfile: () -> Unit = {}
) {
    var filtroSeleccionado by remember { mutableStateOf("Pendientes") }

    val listaCitas = remember {
        mutableStateListOf(
            CitaItem(1, "Carlos Mendoza", "Hoy, 16:00 PM", "Consulta por ansiedad generalizada", "pendiente"),
            CitaItem(2, "María Fernández", "Mañana, 10:00 AM", "Terapia de pareja", "pendiente"),
            CitaItem(3, "Jorge Ramírez", "25 Julio, 18:00 PM", "Seguimiento semanal", "confirmada")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Panel Profesional",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = "Bienvenido, Dr.",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Ir a mi perfil",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
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
            if (!aprobadoPorAdmin) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Tu cédula está en proceso de revisión por el Administrador. Aún no apareces en el catálogo público.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TarjetaEstadistica(
                        titulo = "Pendientes",
                        valor = "${listaCitas.count { it.estado == "pendiente" }}",
                        modifier = Modifier.weight(1f)
                    )
                    TarjetaEstadistica(
                        titulo = "Confirmadas",
                        valor = "${listaCitas.count { it.estado == "confirmada" }}",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Text(
                    text = "Gestión de Citas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = filtroSeleccionado == "Pendientes",
                        onClick = { filtroSeleccionado = "Pendientes" },
                        label = { Text("Pendientes") }
                    )
                    FilterChip(
                        selected = filtroSeleccionado == "Confirmadas",
                        onClick = { filtroSeleccionado = "Confirmadas" },
                        label = { Text("Confirmadas") }
                    )
                }
            }

            val citasFiltradas = listaCitas.filter {
                if (filtroSeleccionado == "Pendientes") it.estado == "pendiente" else it.estado == "confirmada"
            }

            if (citasFiltradas.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay citas en esta categoría",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                items(citasFiltradas) { cita ->
                    TarjetaCitaPsicologo(
                        cita = cita,
                        onAceptar = {
                            val index = listaCitas.indexOf(cita)
                            if (index != -1) {
                                listaCitas[index] = cita.copy(estado = "confirmada")
                            }
                        },
                        onRechazar = { listaCitas.remove(cita) }
                    )
                }
            }
        }
    }
}
@Composable
fun TarjetaSolicitudPsicologo(
    solicitud: SolicitudPsicologo,
    onAprobar: () -> Unit,
    onRechazar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = solicitud.nombre, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text(text = "Especialidad: ${solicitud.especialidad}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Cédula: ${solicitud.cedula}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Precio sesión: $${solicitud.precioSesion}", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = onRechazar) {
                    Text("Rechazar")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onAprobar) {
                    Text("Aprobar")
                }
            }
        }
    }
}

@Composable
fun TarjetaCitaPsicologo(
    cita: CitaItem,
    onAceptar: () -> Unit,
    onRechazar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = cita.nombrePaciente, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text(text = "Horario: ${cita.fechaHora}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Motivo: ${cita.motivo}", style = MaterialTheme.typography.bodySmall)

            if (cita.estado == "pendiente") {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(onClick = onRechazar) {
                        Text("Rechazar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onAceptar) {
                        Text("Aceptar Cita")
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaEstadistica(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = valor,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}