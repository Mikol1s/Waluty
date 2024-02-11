package com.example.waluty.api

import com.example.waluty.data.exchangeRates.ExchangeRatesItem
import com.example.waluty.data.exchangeRatesRates.ExchangeRatesRatesItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/api/exchangerates/tables/A?format=json")
    fun getExchangeRatesTable(): Call<List<ExchangeRatesItem>?>

    @GET("/api/exchangerates/rates/A/{currency}/{dateStart}/{dateEnd}/?format=json")
    fun getExchangeRatesRates(
        @Path("currency") currency: String,
        @Path("dateStart") dateStart: String,
        @Path("dateEnd") dateEnd: String,
    ): Call<ExchangeRatesRatesItem>
}