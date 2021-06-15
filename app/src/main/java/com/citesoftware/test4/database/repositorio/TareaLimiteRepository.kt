package com.citesoftware.test4.database.repositorio

import androidx.lifecycle.LiveData
import com.citesoftware.test4.database.DAOs.TareaLimiteDAO
import com.citesoftware.test4.database.model.TareaLimite

class TareaLimiteRepository(private val tareaLimiteDAO: TareaLimiteDAO) {

    val readAllData: LiveData<MutableList<TareaLimite>> = tareaLimiteDAO.readAllData()

    suspend fun addTareaLimite(tareaLimite: TareaLimite){
        tareaLimiteDAO.addTareaLimite(tareaLimite)
    }

    suspend fun updateTareaLimite(tareaLimite: TareaLimite){
        tareaLimiteDAO.actualizarTareaLimite(tareaLimite)
    }

    suspend fun deleteTareaLimite(tareaLimite: TareaLimite){
        tareaLimiteDAO.eliminarTareaLimite(tareaLimite)
    }

    suspend fun deleteAll(){
        tareaLimiteDAO.eliminarTodo()
    }


}