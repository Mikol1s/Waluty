package com.example.waluty.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExchangeRatesTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromRatesList(rates: List<DBRate>): String {
        return gson.toJson(rates)
    }

    @TypeConverter
    fun toRatesList(ratesString: String): List<DBRate> {
        val type = object : TypeToken<List<DBRate>>() {}.type
        return gson.fromJson(ratesString, type)
    }
}