package com.example.waluty.widgets.utils

import com.github.mikephil.charting.formatter.ValueFormatter

class DateValueFormatter(private val dates: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val index = value.toInt()
        return if (index >= 0 && index < dates.size) {
            dates[index]
        } else {
            ""
        }
    }
}