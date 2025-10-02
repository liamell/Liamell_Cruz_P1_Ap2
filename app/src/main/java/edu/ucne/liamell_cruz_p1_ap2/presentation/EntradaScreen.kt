package edu.ucne.liamell_cruz_p1_ap2.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.liamell_cruz_p1_ap2.data.local.entities.EntradasEntity
import edu.ucne.liamell_cruz_p1_ap2.data.local.repositories.EntradaRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun EntradaScreen(
    entradaRepository: EntradaRepository
) {
    var idEntrada by remember { mutableStateOf<Int?>(null) }
    var fechaTexto by remember { mutableStateOf("") }
    var nombreCliente by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf(null) }

    val scope = rememberCoroutineScope()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        label = { Text("Fecha (yymmdd)") },
                        value = fechaTexto,
                        onValueChange = { fechaTexto = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text("Nombre Cliente") },
                        value = nombreCliente,
                        onValueChange = { nombreCliente = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text("Cantidad") },
                        value = cantidad,
                        onValueChange = { cantidad = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text("Precio") },
                        value = precio,
                        onValueChange = { precio = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.padding(4.dp))
                    errorMessage?.let { Text(text = it, color = Color.Red) }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Botón nuevo
                        OutlinedButton(onClick = {
                            idEntrada = null
                            fechaTexto = ""
                            nombreCliente = ""
                            cantidad = ""
                            precio = ""
                            errorMessage = ""
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Nuevo")
                            Text("Nuevo")
                        }

                        OutlinedButton(onClick = {
                            try {
                                if (nombreCliente.isBlank() || cantidad.isBlank() || precio.isBlank() || fechaTexto.isBlank()) {
                                    errorMessage = "Todos los campos son requeridos"
                                    return@OutlinedButton
                                }


                                val formatter = DateTimeFormatter.ofPattern("yyMMdd")
                                val fecha = LocalDate.parse(fechaTexto, formatter)


                                scope.launch {
                                    entradaRepository.save(
                                        EntradasEntity(
                                            idEntrada = idEntrada,
                                            fecha = fecha,
                                            nombreCliente = nombreCliente,
                                            cantidad = cantidad.toInt(),
                                            precio = precio.toDouble()
                                        )
                                    )
                                    // Limpiamos
                                    idEntrada = null
                                    fechaTexto = ""
                                    nombreCliente = ""
                                    cantidad = ""
                                    precio = ""
                                    errorMessage = ""
                                }
                            } catch (e: Exception) {
                                errorMessage = "Error: ${e.message}"
                            }
                        }) {
                            Icon(Icons.Default.Check, contentDescription = "Guardar")
                            Text(if (idEntrada == null) "Guardar" else "Actualizar")
                        }
                    }
                }
            }

            // Lista de entradas
            val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
            val entradaList by entradaRepository.getAll()
                .collectAsStateWithLifecycle(
                    initialValue = emptyList(),
                    lifecycleOwner = lifecycleOwner,
                    minActiveState = Lifecycle.State.STARTED
                )

            EntradaListScreen(
                entradaList = entradaList,
                onDelete = { entrada ->
                    scope.launch { entradaRepository.delete(entrada) }
                },
                onEdit = { entrada ->
                    idEntrada = entrada.idEntrada
                    fechaTexto = entrada.fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    nombreCliente = entrada.nombreCliente
                    cantidad = entrada.cantidad.toString()
                    precio = entrada.precio.toString()
                }
            )
        }
    }
}
