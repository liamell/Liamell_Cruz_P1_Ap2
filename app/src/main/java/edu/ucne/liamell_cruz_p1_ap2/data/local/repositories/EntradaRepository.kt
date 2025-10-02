package edu.ucne.liamell_cruz_p1_ap2.data.local.repositories

import edu.ucne.liamell_cruz_p1_ap2.data.local.dao.EntradaDao
import edu.ucne.liamell_cruz_p1_ap2.data.local.entities.EntradasEntity
import javax.inject.Inject

class EntradaRepository @Inject constructor(
    private val entradaDao: EntradaDao
)
{
    suspend fun save(entrada: EntradasEntity) = entradaDao.save(entrada)
    suspend fun find(id: Int) = entradaDao.find(id)
    suspend fun delete(entrada: EntradasEntity) = entradaDao.delete(entrada)

    fun getAll() = entradaDao.getAll()
}