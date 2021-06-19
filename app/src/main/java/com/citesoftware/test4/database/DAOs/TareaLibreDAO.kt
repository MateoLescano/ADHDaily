package com.citesoftware.test4.database.DAOs

import androidx.lifecycle.LiveData
import androidx.room.*
import com.citesoftware.test4.database.model.TareaLibre

@Dao
interface TareaLibreDAO {

    //Suspend se usa cuando la funcion usará coroutines

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTareaLibre(tareaLibre: TareaLibre)

    @Query("SELECT * FROM tabla_tareaLibre ORDER BY exportar,~proxima, id ASC")
    fun readAllData(): LiveData<MutableList<TareaLibre>>

    @Update
    suspend fun actualizarTareaLibre(tareaLibre: TareaLibre)

    @Delete
    suspend fun eliminarTareaLibre(tareaLibre: TareaLibre)

    @Query("DELETE FROM tabla_tareaLibre")
    suspend fun eliminarTodo()



}

