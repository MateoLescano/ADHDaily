package com.citesoftware.test4.database.repositorio

import androidx.lifecycle.LiveData
import com.citesoftware.test4.database.DAOs.TareaLibreDAO
import com.citesoftware.test4.database.model.TareaLibre

class TareaLibreRepository(private val tareaLibreDAO: TareaLibreDAO) {

    val readAllData: LiveData<MutableList<TareaLibre>> = tareaLibreDAO.readAllData()

    suspend fun addTareaLibre(tareaLibre: TareaLibre){
        tareaLibreDAO.addTareaLibre(tareaLibre)
    }

    suspend fun updateTareaLibre(tareaLibre: TareaLibre){
        tareaLibreDAO.actualizarTareaLibre(tareaLibre)
    }

    suspend fun deleteTareaLibre(tareaLibre: TareaLibre){
        tareaLibreDAO.eliminarTareaLibre(tareaLibre)
    }

    suspend fun deleteAll(){
        tareaLibreDAO.eliminarTodo()
    }

}