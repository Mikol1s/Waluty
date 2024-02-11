package com.example.waluty.usecase.exchangesdata

import com.example.waluty.NetworkChecker
import com.example.waluty.api.Result
import com.example.waluty.data.exchangeRates.ExchangeRatesItem
import com.example.waluty.data.exchangeRatesRates.ExchangeRatesRatesItem
import java.util.Date

class ExchangesDataUseCase(
    val fetchDataRepository: ExchangesDataRepository,
    val networkChecker: NetworkChecker
) {
    fun getResultExchangeRates(): Result<List<ExchangeRatesItem>> {
        return if (networkChecker.isNetworkAvailable()) {
            fetchDataRepository.getExchangeRates()
        } else {
            Result.NoInternetConnection
        }
    }

    fun getExchangeRatesRates(
        currency: String,
        startDate: Date,
        endDate: Date
    ): Result<ExchangeRatesRatesItem> {
        return if (networkChecker.isNetworkAvailable()) {
            val exchangeRatesRates =
                fetchDataRepository.getExchangeRatesRates(currency, startDate, endDate)
            exchangeRatesRates
        } else {
            Result.NoInternetConnection
        }
    }
}