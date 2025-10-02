package edu.ucne.liamell_cruz_p1_ap2.presentation

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.liamell_cruz_p1_ap2.data.local.entities.EntradasEntity
import edu.ucne.liamell_cruz_p1_ap2.data.local.repositories.EntradaRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun EntradaScreen(entradaRepository: EntradaRepository) {
    var idEntrada by remember { mutableStateOf<Int?>(null) }
    var fecha by remember { mutableStateOf(LocalDate.now()) }
    var nombreCliente by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val entradaList by entradaRepository.getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {

            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    FechaInputField(fecha) { fecha = it }

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

                    Spacer(modifier = Modifier.height(4.dp))
                    errorMessage?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Botón Nuevo
                        OutlinedButton(onClick = {
                            idEntrada = null
                            fecha = LocalDate.now()
                            nombreCliente = ""
                            cantidad = ""
                            precio = ""
                            errorMessage = ""
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Nuevo")
                            Text("Nuevo")
                        }

                        // Botón Guardar / Actualizar
                        OutlinedButton(onClick = {
                            if (nombreCliente.isBlank() || cantidad.isBlank() || precio.isBlank()) {
                                errorMessage = "Todos los campos son requeridos"
                                return@OutlinedButton
                            }

                            scope.launch {
                                try {
                                    entradaRepository.save(
                                        EntradasEntity(
                                            idEntrada = idEntrada,
                                            fecha = fecha,
                                            nombreCliente = nombreCliente,
                                            cantidad = cantidad.toInt(),
                                            precio = precio.toDouble()
                                        )
                                    )

                                    idEntrada = null
                                    fecha = LocalDate.now()
                                    nombreCliente = ""
                                    cantidad = ""
                                    precio = ""
                                    errorMessage = ""
                                } catch (e: Exception) {
                                    errorMessage = "Error: ${e.message}"
                                }
                            }
                        }) {
                            Icon(Icons.Default.Check, contentDescription = "Guardar")
                            Text(if (idEntrada == null) "Guardar" else "Actualizar")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            val listScope = rememberCoroutineScope()
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(entradaList) { entrada ->
                    EntradaRow(
                        entrada,
                        onDelete = { e -> listScope.launch { entradaRepository.delete(e) } },
                        onEdit = { e ->
                            idEntrada = e.idEntrada
                            fecha = e.fecha
                            nombreCliente = e.nombreCliente
                            cantidad = e.cantidad.toString()
                            precio = e.precio.toString()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FechaInputField(fecha: LocalDate, onFechaChange: (LocalDate) -> Unit) {
    val context = LocalContext.current
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onFechaChange(LocalDate.of(year, month + 1, dayOfMonth))
        },
        fecha.year,
        fecha.monthValue - 1,
        fecha.dayOfMonth
    )

    OutlinedTextField(
        value = fecha.format(formatter),
        onValueChange = {},
        label = { Text("Fecha") },
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(Icons.Default.Add, contentDescription = "Seleccionar fecha")
            }
        }
    )
}


@Composable
private fun EntradaRow(
    entrada: EntradasEntity,
    onDelete: (EntradasEntity) -> Unit,
    onEdit: (EntradasEntity) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yy", Locale.getDefault())

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = entrada.fecha.format(formatter),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = entrada.nombreCliente,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(2f)
                )
                Text(
                    text = "$${"%.2f".format(entrada.precio)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Cantidad: ${entrada.cantidad}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onEdit(entrada) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = { onDelete(entrada) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}
