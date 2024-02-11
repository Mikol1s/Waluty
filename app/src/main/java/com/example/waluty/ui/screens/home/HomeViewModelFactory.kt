package com.example.waluty.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.waluty.NetworkChecker
import com.example.waluty.usecase.database.DatabaseUseCase
import com.example.waluty.usecase.exchangesdata.ExchangesDataUseCase
import com.example.waluty.usecase.storedata.DataStoreUseCase

class HomeViewModelFactory(
    private val exchangesDataUseCase: ExchangesDataUseCase,
    private val dataStoreUseCase: DataStoreUseCase,
    private val databaseUseCase: DatabaseUseCase,
    private val networkChecker: NetworkChecker
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(exchangesDataUseCase, dataStoreUseCase, databaseUseCase, networkChecker) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}