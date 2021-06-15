package com.citesoftware.test4.database.DBs

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.citesoftware.test4.database.DAOs.TareaEventoDAO
import com.citesoftware.test4.database.model.TareaEvento

@Database(entities = [TareaEvento::class], version = 2, exportSchema = false)
abstract class TareaEventoDatabase: RoomDatabase() {

    abstract fun tareaEventoDAO(): TareaEventoDAO

    companion object{
        @Volatile
        private var INSTANCE: TareaEventoDatabase? = null

        val migration1to2: Migration = object: Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE 'tabla_tareaEvento' ADD COLUMN 'color' TEXT NOT NULL DEFAULT 'Sin Color'")
            }
        }


        fun getDatabase(context: Context): TareaEventoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TareaEventoDatabase::class.java,
                        "tareaEvento_database"
                ).addMigrations(migration1to2).build()
                INSTANCE = instance
                return instance
            }
        }

    }


}