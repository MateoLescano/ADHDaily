package com.citesoftware.test4.database.DAOs

import androidx.lifecycle.LiveData
import androidx.room.*
import com.citesoftware.test4.database.model.TareaLimite

@Dao
interface TareaLimiteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTareaLimite(tareaLibre: TareaLimite)

    @Query("SELECT * FROM tabla_tareaLimite ORDER BY completado, mes, dia ASC")
    fun readAllData(): LiveData<MutableList<TareaLimite>>

    @Update
    suspend fun actualizarTareaLimite(tareaLibre: TareaLimite)

    @Delete
    suspend fun eliminarTareaLimite(tareaLibre: TareaLimite)

    @Query("DELETE FROM tabla_tareaLimite")
    suspend fun eliminarTodo()


}