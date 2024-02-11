package com.example.waluty.usecase.storedata

interface StoreDataRepository {
    suspend fun addFavouriteCurrency(code: String)
    suspend fun removeFavouriteCurrency(code: String)
    suspend fun isFavouriteCurrency(code: String): Boolean
    suspend fun setName(name: String)
    suspend fun getName(): String
    suspend fun setDarkMode(isDarkMode: Boolean)
    suspend fun isDarkMode(): Boolean
}
