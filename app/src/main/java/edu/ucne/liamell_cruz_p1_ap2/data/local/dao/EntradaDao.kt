package edu.ucne.liamell_cruz_p1_ap2.data.local.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.liamell_cruz_p1_ap2.data.local.entities.EntradasEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface EntradaDao {
    @Upsert()
    suspend fun save(Entrada: EntradasEntity)
    @Query(
        """SELECT * FROM Entradas WHERE IdEntrada == :id limit 1"""
    )
    suspend fun find(id: Int): EntradasEntity?
    @Delete
    suspend fun delete(Entrada: EntradasEntity)
    @Query("SELECT * FROM Entradas")
    fun getAll(): Flow<List<EntradasEntity>>
}
