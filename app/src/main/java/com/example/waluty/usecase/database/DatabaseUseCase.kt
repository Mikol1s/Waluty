package com.example.waluty.usecase.database

import com.example.waluty.database.ExchangeRatesEntity

class DatabaseUseCase(
    val databaseRepository: DatabaseRepository
) {
    suspend fun getExchangeRatesEntity(): ExchangeRatesEntity? {
        return databaseRepository.getExchangeRatesFirst()
    }

    suspend fun insertExchangeRates(exchangeRatesEntity: ExchangeRatesEntity) {
        return databaseRepository.insertExchangeRates(exchangeRatesEntity)
    }

    suspend fun deleteExchangeRates() {
        return databaseRepository.deleteExchangeRates()
    }

}
