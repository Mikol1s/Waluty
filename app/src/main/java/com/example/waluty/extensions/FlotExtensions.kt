package com.example.waluty.extensions

import java.text.DecimalFormat

fun Float.toFormat(pattern: String): String {
    val decimalFormat = DecimalFormat(pattern)
    return decimalFormat.format(this)
}