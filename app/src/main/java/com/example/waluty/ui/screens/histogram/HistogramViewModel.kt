package com.example.waluty.ui.screens.histogram

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waluty.api.Result
import com.example.waluty.data.exchangeRatesRates.ExchangeRatesRatesItem
import com.example.waluty.usecase.exchangesdata.ExchangesDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class HistogramViewModel(
    private val downloadDataUseCase: ExchangesDataUseCase
) : ViewModel() {

    private val TAG = this::class.simpleName

    private val _exchangeRatesRates = MutableLiveData<ExchangeRatesRatesItem>()//
    val exchangeRatesRates: LiveData<ExchangeRatesRatesItem> get() = _exchangeRatesRates
    // livedata -> zwraca wartosci, sa pozyskiwane z  linijki 22 (jednokierunkowa modyfikacja)
    fun getExchangeRatesRates(currency: String, startDate: Date, endDate: Date) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {

                val exchangeRates =
                    downloadDataUseCase.getExchangeRatesRates(
                        currency = currency,
                        startDate = startDate,
                        endDate = endDate
                    )

                withContext(Dispatchers.Main) {
                    when (exchangeRates) {
                        is Result.Success -> {
                            _exchangeRatesRates.value = exchangeRates.data
                        }

                        is Result.Error -> {
                            Log.e(TAG, "getExchangeRatesRates: ${exchangeRates.exception}")
                        }

                        Result.NoInternetConnection -> {
                            Log.e(TAG, "getExchangeRatesRates: NoInternetConnection")
                        }
                    }
                }
            }
        }
    }

    fun calcMinValue(exchangeRatesRates: ExchangeRatesRatesItem?) =
        exchangeRatesRates?.rates?.minOfOrNull { it.mid } ?: 0f

    fun calcMaxValue(exchangeRatesRates: ExchangeRatesRatesItem?) =
        exchangeRatesRates?.rates?.maxOfOrNull { it.mid } ?: 0f

    fun calcMedianValue(minValue: Float, maxValue: Float) =
        (minValue + maxValue) / 2f
}