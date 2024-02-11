package com.example.waluty.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.waluty.usecase.storedata.DataStoreUseCase

class SettingsViewModelFactory(
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(dataStoreUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}