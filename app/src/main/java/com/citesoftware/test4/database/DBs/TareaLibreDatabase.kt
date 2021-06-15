package com.citesoftware.test4.database.DBs

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.citesoftware.test4.database.DAOs.TareaLibreDAO
import com.citesoftware.test4.database.model.TareaLibre

@Database(entities = [TareaLibre::class], version = 2, exportSchema = false)
abstract class TareaLibreDatabase: RoomDatabase() {

    abstract fun tareaLibreDAO(): TareaLibreDAO

    companion object{
        @Volatile
        private var INSTANCE: TareaLibreDatabase? = null

        val migration1to2: Migration = object: Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE 'tabla_tareaLibre' ADD COLUMN 'color' TEXT NOT NULL DEFAULT 'Sin Color'")
            }
        }


        fun getDatabase(context: Context): TareaLibreDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TareaLibreDatabase::class.java,
                    "tareaLibre_database"
                ).addMigrations(migration1to2).build()
                INSTANCE = instance
                return instance
            }
        }



    }


}