package com.example.waluty.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "exchange_rates")
@TypeConverters(ExchangeRatesTypeConverter::class)
data class ExchangeRatesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val table: String,
    val no: String,
    val effectiveDate: String,
    val rates: List<DBRate>
)