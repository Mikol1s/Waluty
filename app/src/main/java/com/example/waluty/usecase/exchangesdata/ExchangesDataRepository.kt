package com.example.waluty.usecase.exchangesdata

import com.example.waluty.api.Result
import com.example.waluty.data.exchangeRates.ExchangeRatesItem
import com.example.waluty.data.exchangeRatesRates.ExchangeRatesRatesItem
import java.util.Date

interface ExchangesDataRepository {
    fun getExchangeRates(): Result<List<ExchangeRatesItem>>

    fun getExchangeRatesRates(
        currency: String,
        startDate: Date,
        endDate: Date
    ): Result<ExchangeRatesRatesItem>
}