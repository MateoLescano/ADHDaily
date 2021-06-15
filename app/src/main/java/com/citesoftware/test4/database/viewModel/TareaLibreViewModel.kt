package com.citesoftware.test4.database.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.citesoftware.test4.database.DBs.TareaLibreDatabase
import com.citesoftware.test4.database.model.TareaLibre
import com.citesoftware.test4.database.repositorio.TareaLibreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TareaLibreViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<MutableList<TareaLibre>>
    private val repository: TareaLibreRepository

    init {
        val tareaLibreDAO = TareaLibreDatabase.getDatabase(application).tareaLibreDAO()
        repository = TareaLibreRepository(tareaLibreDAO)
        readAllData = repository.readAllData

    }


    fun addTareaLibre(tareaLibre: TareaLibre){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTareaLibre(tareaLibre)
        }
    }

    fun updateTareaLibre(tareaLibre: TareaLibre){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTareaLibre(tareaLibre)
        }
    }

    fun deleteTareaLibre(tareaLibre: TareaLibre){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTareaLibre(tareaLibre)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

}