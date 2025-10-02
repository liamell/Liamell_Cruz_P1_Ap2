//package edu.ucne.liamell_cruz_p1_ap2.di
//
//import android.content.Context
//import androidx.room.Room
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import edu.ucne.liamell_cruz_p1_ap2.data.local.database.EntradaDb
//import javax.inject.Singleton
//
//@InstallIn(SingletonComponent::class)
//@Module
//object AppModule {
//    @Provides
//    @Singleton
//    fun ProvidesEntradaDb(@ApplicationContext appContext: Context) =
//        Room.databaseBuilder(
//            appContext,
//            EntradaDb::class.java,
//            "Entrada.db"
//        ).fallbackToDestructiveMigration()
//            .build()
//
//    @Provides
//    @Singleton
//    fun ProvidesEntradaDao(entradaDb: EntradaDb) = entradaDb.entradaDao()
//
//
//}
