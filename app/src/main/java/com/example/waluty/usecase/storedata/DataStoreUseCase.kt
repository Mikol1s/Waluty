package com.example.waluty.usecase.storedata

class DataStoreUseCase(
    val storeDataRepository: StoreDataRepository
) {
    suspend fun addFavouriteCurrency(code: String) {
        storeDataRepository.addFavouriteCurrency(code)
    }

    suspend fun removeFavouriteCurrency(code: String) {
        storeDataRepository.removeFavouriteCurrency(code)
    }

    suspend fun isFavouriteCurrency(code: String): Boolean {
        return storeDataRepository.isFavouriteCurrency(code)
    }

    suspend fun setName(name: String) {
        return storeDataRepository.setName(name)
    }

    suspend fun getName(): String {
        return storeDataRepository.getName()
    }

    suspend fun isDarkMode(): Boolean {
        return storeDataRepository.isDarkMode()
    }

    suspend fun setDarkMode(isDarkMode: Boolean) {
        return storeDataRepository.setDarkMode(isDarkMode)
    }
}