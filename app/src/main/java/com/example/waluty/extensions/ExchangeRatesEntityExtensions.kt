package com.example.waluty.extensions

import com.example.waluty.data.exchangeRates.ExchangeRatesItem
import com.example.waluty.database.ExchangeRatesEntity

fun ExchangeRatesEntity.toExchangeRatesItem(): ExchangeRatesItem {
    return ExchangeRatesItem(
        table = table,
        no = no,
        effectiveDate = effectiveDate,
        rates = rates.map { it.toRate() }
    )
}