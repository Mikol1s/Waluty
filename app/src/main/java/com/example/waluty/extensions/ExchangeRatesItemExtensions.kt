package com.example.waluty.extensions

import com.example.waluty.data.exchangeRates.ExchangeRatesItem
import com.example.waluty.database.ExchangeRatesEntity

fun ExchangeRatesItem.toExchangeRatesEntity(): ExchangeRatesEntity {
    return ExchangeRatesEntity(
        table = table,
        no = no,
        effectiveDate = effectiveDate,
        rates = rates.map { it.toDBRate() }
    )
}