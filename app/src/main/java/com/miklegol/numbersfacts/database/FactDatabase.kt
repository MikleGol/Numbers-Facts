package com.miklegol.numbersfacts.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miklegol.numbersfacts.models.Fact

@Database(entities = [Fact::class], version = 1, exportSchema = false)
abstract class FactDatabase : RoomDatabase() {

    abstract fun factDao(): FactDao

    companion object {
        @Volatile
        private var INSTANCE: FactDatabase? = null

        @Synchronized
        fun getInstance(context : Context) : FactDatabase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, FactDatabase::class.java, "fact.db").fallbackToDestructiveMigration().build()
            }
            return INSTANCE as FactDatabase
        }
    }
}
