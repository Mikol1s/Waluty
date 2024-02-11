package com.example.waluty.extensions

import com.example.waluty.data.exchangeRates.Rate
import com.example.waluty.database.DBRate

fun Rate.toDBRate(): DBRate {
    return DBRate(
        currency = currency,
        code = code,
        mid = mid
    )
}