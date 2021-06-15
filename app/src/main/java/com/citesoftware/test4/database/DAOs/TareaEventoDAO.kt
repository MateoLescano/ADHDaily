package com.citesoftware.test4.database.DAOs

import androidx.lifecycle.LiveData
import androidx.room.*
import com.citesoftware.test4.database.model.TareaEvento

@Dao
interface TareaEventoDAO {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTareaEvento(tareaLibre: TareaEvento)

    @Query("SELECT * FROM tabla_tareaEvento ORDER BY completado,mes,dia,horario ASC")
    fun readAllData(): LiveData<MutableList<TareaEvento>>

    @Update
    suspend fun actualizarTareaEvento(tareaLibre: TareaEvento)

    @Delete
    suspend fun eliminarTareaEvento(tareaLibre: TareaEvento)

    @Query("DELETE FROM tabla_tareaEvento")
    suspend fun eliminarTodo()

}