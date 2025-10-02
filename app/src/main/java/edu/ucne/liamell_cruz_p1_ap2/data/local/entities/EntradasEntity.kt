package edu.ucne.liamell_cruz_p1_ap2.data.local.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "Entradas")
data class EntradasEntity(
    @PrimaryKey
    val idEntrada: Int? = null,
    val fecha: LocalDate,
    val nombreCliente: String,
    val cantidad: Int,
    val precio : Double
)
