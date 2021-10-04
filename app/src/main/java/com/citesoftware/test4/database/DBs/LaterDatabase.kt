package com.citesoftware.test4.database.DBs


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.citesoftware.test4.database.DAOs.LaterDAO
import com.citesoftware.test4.database.model.Later

@Database(entities = [Later::class], version = 1, exportSchema = false)
abstract class LaterDatabase: RoomDatabase() {

    abstract fun LaterDAO(): LaterDAO

    companion object{
        @Volatile
        private var INSTANCE: LaterDatabase? = null



        fun getDatabase(context: Context): LaterDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LaterDatabase::class.java,
                    "later_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }

    }


}