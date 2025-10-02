package edu.ucne.liamell_cruz_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.liamell_cruz_p1_ap2.data.local.ClassConverter.Converters
import edu.ucne.liamell_cruz_p1_ap2.data.local.dao.EntradaDao
import edu.ucne.liamell_cruz_p1_ap2.data.local.entities.EntradasEntity


@Database(
    entities = [
        EntradasEntity::class,


    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class EntradaDb : RoomDatabase() {
    abstract fun EntradaDao(): EntradaDao

}

