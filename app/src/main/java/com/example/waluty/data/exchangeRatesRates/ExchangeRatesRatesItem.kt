package com.example.waluty.data.exchangeRatesRates

data class ExchangeRatesRatesItem(
    val table: String,
    val currency: String,
    val code: String,
    val rates: List<Rates>,
)