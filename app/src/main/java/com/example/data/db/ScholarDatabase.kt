package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ScholarmonEntity::class, PlayerProgressEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ScholarDatabase : RoomDatabase() {
    abstract fun scholarDao(): ScholarDao

    companion object {
        @Volatile
        private var INSTANCE: ScholarDatabase? = null

        fun getInstance(context: Context): ScholarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScholarDatabase::class.java,
                    "scholar_journey_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
