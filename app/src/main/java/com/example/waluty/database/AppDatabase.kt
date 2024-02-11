package com.example.waluty.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.waluty.extensions.applicationContextSafe
import java.util.concurrent.Executors

@Database(entities = [ExchangeRatesEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exchangeRatesDao(): ExchangeRatesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContextSafe,
                    AppDatabase::class.java,
                    "app_database"
                ).apply {
                    setQueryCallback(RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
                        println("SQL Query: $sqlQuery SQL Args: $bindArgs")
                    }, Executors.newSingleThreadExecutor())
                }.build()
                INSTANCE = instance
                instance
            }
        }
    }
}

