package com.example.waluty.usecase.exchangesdata

import com.example.waluty.api.ApiService
import com.example.waluty.api.Result
import com.example.waluty.data.exchangeRates.ExchangeRatesItem
import com.example.waluty.data.exchangeRatesRates.ExchangeRatesRatesItem
import com.example.waluty.utils.toDateFormat
import java.util.Date

class ExchangesDataRepositoryImpl(
    val apiService: ApiService,
) : ExchangesDataRepository {

    override fun getExchangeRates(): Result<List<ExchangeRatesItem>> {
        return try {
            val response = apiService.getExchangeRatesTable().execute()

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: run {
                    Result.Error(IllegalArgumentException("${response.code()}|body:null"))
                }
            } else {
                Result.Error(IllegalArgumentException("${response.code()}|body:${response.errorBody()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun getExchangeRatesRates(
        currency: String,
        startDate: Date,
        endDate: Date
    ): Result<ExchangeRatesRatesItem> {
        return try {
            val response = apiService.getExchangeRatesRates(
                currency = currency,
                dateStart = startDate.toDateFormat(),
                dateEnd = endDate.toDateFormat()
            ).execute()

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: run {
                    Result.Error(IllegalArgumentException("${response.code()}|body:null"))
                }
            } else {
                Result.Error(IllegalArgumentException("${response.code()}|body:${response.errorBody()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}