package edu.ucne.liamell_cruz_p1_ap2.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNavigateToEntradas: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "EntradasHuacales")

            Button(
                onClick = onNavigateToEntradas,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
                Text(text = "Ir a Entradas", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}
