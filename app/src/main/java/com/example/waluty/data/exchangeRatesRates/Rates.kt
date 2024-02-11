package com.example.waluty.data.exchangeRatesRates

import com.example.waluty.extensions.toFormat

data class Rates(
    val no: String,
    val effectiveDate: String,
    val mid: Float
) {
    override fun toString(): String {
        return "Rates" +
                "(" +
                "no='$no', " +
                "effectiveDate='$effectiveDate', " +
                "mid=${mid.toFormat("0.00000")}" +
                ")"
    }
}