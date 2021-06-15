package com.citesoftware.test4.database.repositorio

import androidx.lifecycle.LiveData
import com.citesoftware.test4.database.DAOs.TareaEventoDAO
import com.citesoftware.test4.database.model.TareaEvento

class TareaEventoRepository(private val tareaEventoDAO: TareaEventoDAO) {

    val readAllData: LiveData<MutableList<TareaEvento>> = tareaEventoDAO.readAllData()

    suspend fun addTareaEvento(tareaEvento: TareaEvento){
        tareaEventoDAO.addTareaEvento(tareaEvento)
    }

    suspend fun updateTareaEvento(tareaEvento: TareaEvento){
        tareaEventoDAO.actualizarTareaEvento(tareaEvento)
    }

    suspend fun deleteTareaEvento(tareaEvento: TareaEvento){
        tareaEventoDAO.eliminarTareaEvento(tareaEvento)
    }

    suspend fun deleteAll(){
        tareaEventoDAO.eliminarTodo()
    }


}