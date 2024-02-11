package com.example.waluty.usecase.database

import com.example.waluty.database.AppDatabase
import com.example.waluty.database.ExchangeRatesEntity

class DatabaseRepositoryImpl(val database : AppDatabase) : DatabaseRepository {

    override suspend fun getExchangeRatesFirst(): ExchangeRatesEntity? {
        return database.exchangeRatesDao().getExchangeRatesFirst()
    }

    override suspend fun insertExchangeRates(exchangeRatesEntity: ExchangeRatesEntity) {
        database.exchangeRatesDao().insertExchangeRates(exchangeRatesEntity)
    }

    override suspend fun deleteExchangeRates() {
        database.exchangeRatesDao().deleteExchangeRates()
    }
}