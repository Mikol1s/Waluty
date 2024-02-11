package com.example.waluty.data.exchangeRates

data class Rate(
    val currency: String,
    val code: String,
    val mid: Float
)