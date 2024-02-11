package com.example.waluty.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waluty.usecase.storedata.DataStoreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    fun setName(name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                dataStoreUseCase.setName(name)
            }
        }
    }

    fun getName(): String = runBlocking {
        withContext(Dispatchers.Default) {
            return@withContext dataStoreUseCase.getName()
        }
    }

    fun isDarkMode(): Boolean = runBlocking {
        withContext(Dispatchers.Default) {
            return@withContext dataStoreUseCase.isDarkMode()
        }
    }

}