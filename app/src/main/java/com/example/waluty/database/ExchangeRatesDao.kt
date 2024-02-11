package com.example.waluty.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ExchangeRatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchangeRates(exchangeRates: ExchangeRatesEntity): Long

    @Query("SELECT * FROM exchange_rates ORDER BY id LIMIT 1")
    suspend fun getExchangeRatesFirst(): ExchangeRatesEntity?

    @Transaction
    @Query("DELETE FROM exchange_rates")
    suspend fun deleteExchangeRates()
}