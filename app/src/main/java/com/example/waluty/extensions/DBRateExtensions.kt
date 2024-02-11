package com.example.waluty.extensions

import com.example.waluty.data.exchangeRates.Rate
import com.example.waluty.database.DBRate

fun DBRate.toRate(): Rate {
    return Rate(
        currency = currency,
        code = code,
        mid = mid
    )
}