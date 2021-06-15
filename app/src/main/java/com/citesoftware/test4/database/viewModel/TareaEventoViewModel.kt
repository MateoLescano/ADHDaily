package com.citesoftware.test4.database.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.citesoftware.test4.database.DBs.TareaEventoDatabase
import com.citesoftware.test4.database.model.TareaEvento
import com.citesoftware.test4.database.repositorio.TareaEventoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TareaEventoViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<MutableList<TareaEvento>>
    private val repository: TareaEventoRepository

    init {
        val tareaEventoDAO = TareaEventoDatabase.getDatabase(application).tareaEventoDAO()
        repository = TareaEventoRepository(tareaEventoDAO)
        readAllData = repository.readAllData

    }

    fun addTareaEvento(tareaEvento: TareaEvento) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTareaEvento(tareaEvento)
        }
    }

    fun updateTareaEvento(tareaEvento: TareaEvento) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTareaEvento(tareaEvento)
        }
    }

    fun deleteTareaEvento(tareaEvento: TareaEvento) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTareaEvento(tareaEvento)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}