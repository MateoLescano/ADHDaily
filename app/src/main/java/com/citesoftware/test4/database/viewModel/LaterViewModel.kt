package com.citesoftware.test4.database.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.citesoftware.test4.database.DBs.LaterDatabase
import com.citesoftware.test4.database.DBs.TareaLibreDatabase
import com.citesoftware.test4.database.model.Later
import com.citesoftware.test4.database.model.TareaLibre
import com.citesoftware.test4.database.repositorio.LaterRepository
import com.citesoftware.test4.database.repositorio.TareaLibreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LaterViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<MutableList<Later>>
    private val repository: LaterRepository

    init {
        val LaterDAO = LaterDatabase.getDatabase(application).LaterDAO()
        repository = LaterRepository(LaterDAO)
        readAllData = repository.readAllData

    }


    fun addLater(later: Later){
        viewModelScope.launch(Dispatchers.IO){
            repository.addLater(later)
        }
    }

    fun updateLater(later: Later){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLater(later)
        }
    }

    fun deleteLater(later: Later){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLater(later)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

}