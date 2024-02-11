package com.example.waluty.usecase.database

import com.example.waluty.database.ExchangeRatesEntity

interface DatabaseRepository {
    suspend fun getExchangeRatesFirst() : ExchangeRatesEntity?
    suspend fun insertExchangeRates(exchangeRatesEntity: ExchangeRatesEntity)
    suspend fun deleteExchangeRates()
}
