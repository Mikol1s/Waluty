package com.example.waluty.ui.screens.histogram

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.waluty.usecase.exchangesdata.ExchangesDataUseCase

class HistogramViewModelFactory(private val exchangesDataUseCase: ExchangesDataUseCase) :
    ViewModelProvider.Factory { // Viewmodel zeby mial parametry to jest potrzebne factory
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistogramViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistogramViewModel(exchangesDataUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}