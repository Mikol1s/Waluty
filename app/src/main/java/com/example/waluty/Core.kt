package com.example.waluty

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.waluty.database.AppDatabase

class Core : Application() {
    // Used to store light data
    val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    // Used to store heavy data
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    val networkChecker by lazy { NetworkChecker(this) }
}