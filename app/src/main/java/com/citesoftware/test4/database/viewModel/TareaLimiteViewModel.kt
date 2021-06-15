package com.citesoftware.test4.database.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.citesoftware.test4.database.DBs.TareaLimiteDatabase
import com.citesoftware.test4.database.model.TareaLimite
import com.citesoftware.test4.database.repositorio.TareaLimiteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TareaLimiteViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<MutableList<TareaLimite>>
    private val repository: TareaLimiteRepository

    init {
        val tareaLimiteDAO = TareaLimiteDatabase.getDatabase(application).tareaLimiteDAO()
        repository = TareaLimiteRepository(tareaLimiteDAO)
        readAllData = repository.readAllData

    }

    fun addTareaLimite(tareaLimite: TareaLimite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTareaLimite(tareaLimite)
        }
    }

    fun updateTareaLimite(tareaLimite: TareaLimite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTareaLimite(tareaLimite)
        }
    }

    fun deleteTareaLimite(tareaLimite: TareaLimite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTareaLimite(tareaLimite)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}