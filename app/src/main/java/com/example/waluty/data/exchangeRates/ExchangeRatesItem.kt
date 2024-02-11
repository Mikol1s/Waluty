package com.example.waluty.data.exchangeRates

data class ExchangeRatesItem(
    val table: String,
    val no: String,
    val effectiveDate: String,
    val rates: List<Rate>,
)