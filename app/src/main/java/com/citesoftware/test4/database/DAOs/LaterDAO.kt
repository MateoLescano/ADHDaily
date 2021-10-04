package com.citesoftware.test4.database.DAOs

import androidx.lifecycle.LiveData
import androidx.room.*
import com.citesoftware.test4.database.model.Later

@Dao
interface LaterDAO {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLater(later: Later)

    @Query("SELECT * FROM tabla_later ORDER BY id ASC")
    fun readAllData(): LiveData<MutableList<Later>>

    @Update
    suspend fun actualizarLater(later: Later)

    @Delete
    suspend fun eliminarLater(later: Later)

    @Query("DELETE FROM tabla_Later")
    suspend fun eliminarTodo()

}