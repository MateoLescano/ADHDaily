package com.citesoftware.test4.database.repositorio

import androidx.lifecycle.LiveData
import com.citesoftware.test4.database.DAOs.LaterDAO
import com.citesoftware.test4.database.model.Later


class LaterRepository(private val laterDAO: LaterDAO) {

    val readAllData: LiveData<MutableList<Later>> = laterDAO.readAllData()

    suspend fun addTareaLibre(later: Later){
        laterDAO.addLater(later)
    }

    suspend fun updateTareaLibre(later: Later){
        laterDAO.actualizarLater(later)
    }

    suspend fun deleteTareaLibre(later: Later){
        laterDAO.eliminarLater(later)
    }

    suspend fun deleteAll(){
        laterDAO.eliminarTodo()
    }

}