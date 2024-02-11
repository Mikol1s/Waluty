package com.example.waluty.extensions

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.waluty.Core
import com.example.waluty.MainActivity
import com.example.waluty.NetworkChecker
import com.example.waluty.database.AppDatabase
import com.example.waluty.usecase.database.DatabaseUseCase
import com.example.waluty.usecase.exchangesdata.ExchangesDataUseCase
import com.example.waluty.usecase.storedata.DataStoreUseCase

// rozszerzenia - mozliwosc dla dowolnej klasy mozemy napisac rozszerzenie, dodatkowe funkcje

val Context.exchangesDataUseCase: ExchangesDataUseCase
    get() = (this as MainActivity).exchangesDataUseCase

val Context.storeDataUseCase: DataStoreUseCase
    get() = (this as MainActivity).dataStoreUseCase

val Context.databaseUseCase: DatabaseUseCase
    get() = (this as MainActivity).databaseUseCase

val Context.applicationContextSafe: Context
    get() = if (this is Application) {
        this
    } else {
        applicationContext
    }

val Context.networkChecker: NetworkChecker
    get() = (applicationContext as Core).networkChecker

val Context.dataStore: DataStore<Preferences>
    get() = (applicationContext as Core).dataStore

val Context.database: AppDatabase
    get() = (applicationContext as Core).database