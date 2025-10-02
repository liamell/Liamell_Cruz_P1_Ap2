package edu.ucne.liamell_cruz_p1_ap2.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucne.liamell_cruz_p1_ap2.data.local.entities.EntradasEntity
import java.time.format.DateTimeFormatter

@Composable
fun EntradaListScreen(
    entradaList: List<EntradasEntity>,
    onDelete: (EntradasEntity) -> Unit,
    onEdit: (EntradasEntity) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(16.dp))
        Text("Lista de Entradas", style = MaterialTheme.typography.titleMedium)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(entradaList) { entrada ->
                EntradaRow(entrada, onDelete, onEdit)
            }
        }
    }
}

@Composable
private fun EntradaRow(
    entrada: EntradasEntity,
    onDelete: (EntradasEntity) -> Unit,
    onEdit: (EntradasEntity) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(modifier = Modifier.weight(1f), text = entrada.idEntrada?.toString() ?: "")
        Text(modifier = Modifier.weight(2f), text = entrada.fecha.format(formatter))
        Text(modifier = Modifier.weight(3f), text = entrada.nombreCliente)
        Text(modifier = Modifier.weight(1f), text = entrada.cantidad.toString())
        Text(modifier = Modifier.weight(1f), text = "$${entrada.precio}")

        IconButton(onClick = { onEdit(entrada) }) {
            Icon(Icons.Default.Edit, contentDescription = "Editar")
        }

        IconButton(onClick = { onDelete(entrada) }) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
    HorizontalDivider()
}
