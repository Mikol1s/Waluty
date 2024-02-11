package com.example.waluty.usecase.storedata

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class StoreDataRepositoryImpl(val dataStore: DataStore<Preferences>) : StoreDataRepository {

    override suspend fun addFavouriteCurrency(code: String) {
        dataStore.edit { preferences ->
            val stringSetPreferencesKey = stringSetPreferencesKey("FAVOURITE_CURRENCY_LIST")
            val currentList: Set<String> = preferences[stringSetPreferencesKey] ?: emptySet()
            val updatedList = currentList.toMutableSet().apply {
                add(code)
            }
            preferences[stringSetPreferencesKey] = updatedList
        }
    }

    override suspend fun removeFavouriteCurrency(code: String) {
        dataStore.edit { preferences ->
            val stringSetPreferencesKey = stringSetPreferencesKey("FAVOURITE_CURRENCY_LIST")
            val currentList: Set<String> = preferences[stringSetPreferencesKey] ?: emptySet()
            val updatedList = currentList.toMutableSet().apply {
                remove(code)
            }
            preferences[stringSetPreferencesKey] = updatedList
        }
    }

    override suspend fun isFavouriteCurrency(code: String): Boolean {
        val stringSetPreferencesKey = stringSetPreferencesKey("FAVOURITE_CURRENCY_LIST")

        val currentList: Set<String> = dataStore.data.map { preferences ->
            preferences[stringSetPreferencesKey] ?: emptySet()
        }.first()

        return currentList.contains(code)
    }

    override suspend fun setName(name: String) {
        dataStore.edit { preferences ->
            val stringPreferencesKey = stringPreferencesKey("SETTINGS_NAME")
            preferences[stringPreferencesKey] = name
        }
    }

    override suspend fun getName(): String {
        val stringPreferencesKey = stringPreferencesKey("SETTINGS_NAME")

        val name: String = dataStore.data.map { preferences ->
            preferences[stringPreferencesKey] ?: ""
        }.first()

        return name
    }

    override suspend fun setDarkMode(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            val booleanPreferencesKey = booleanPreferencesKey("SETTINGS_IS_DARK_MODE")
            preferences[booleanPreferencesKey] = isDarkMode
        }
    }

    override suspend fun isDarkMode(): Boolean {
        val booleanPreferencesKey = booleanPreferencesKey("SETTINGS_IS_DARK_MODE")

        val name: Boolean = dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey] ?: false
        }.first()

        return name
    }
    fun test(){
        var tekst = "demo"
        tekst.repeatWord()
    }

    fun String.repeatWord(): String{
        return ("$this $this")
    }

}


