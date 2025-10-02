package edu.ucne.liamell_cruz_p1_ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import edu.ucne.liamell_cruz_p1_ap2.data.local.database.EntradaDb
import edu.ucne.liamell_cruz_p1_ap2.data.local.entities.EntradasEntity
import edu.ucne.liamell_cruz_p1_ap2.data.local.repositories.EntradaRepository
import edu.ucne.liamell_cruz_p1_ap2.presentation.EntradaListScreen
import edu.ucne.liamell_cruz_p1_ap2.presentation.EntradaScreen
import edu.ucne.liamell_cruz_p1_ap2.ui.theme.Liamell_Cruz_P1_Ap2Theme
import java.time.LocalDate
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    private lateinit var entradaRepository: EntradaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val entradaDb = Room.databaseBuilder(
            applicationContext,
            EntradaDb::class.java,
            "Entradas.db"
        ).fallbackToDestructiveMigration()
            .build()
        entradaRepository = EntradaRepository(entradaDb.EntradaDao())

        setContent {
            Liamell_Cruz_P1_Ap2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        EntradaScreen(entradaRepository)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Liamell_Cruz_P1_Ap2Theme {
        val entradaList = listOf(
            EntradasEntity(
                idEntrada = 1,
                fecha = LocalDate.now(),
                nombreCliente = "Lia mell",
                cantidad = 3,
                precio = 250.0
            )
        )
        EntradaListScreen(
            entradaList = entradaList,
            onDelete = {},
            onEdit = {}
        )
    }
}
