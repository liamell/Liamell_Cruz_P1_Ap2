package edu.ucne.liamell_cruz_p1_ap2.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Lista de Entradas",
            style = MaterialTheme.typography.titleMedium
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
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
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.weight(1f), text = entrada.idEntrada.toString())
        Text(
            modifier = Modifier.weight(2f),
            text = entrada.fecha.format(formatter),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            modifier = Modifier.weight(3f),
            text = entrada.nombreCliente,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(modifier = Modifier.weight(1f), text = entrada.cantidad.toString())
        Text(modifier = Modifier.weight(1f), text = "$${entrada.precio}")

        // Botón editar
        IconButton(onClick = { onEdit(entrada) }) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar entrada"
            )
        }

        // Botón eliminar
        IconButton(onClick = { onDelete(entrada) }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar entrada"
            )
        }
    }
    HorizontalDivider()
}

