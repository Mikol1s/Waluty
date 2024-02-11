package com.example.waluty.ui.screens.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waluty.NetworkChecker
import com.example.waluty.api.Result
import com.example.waluty.data.exchangeRates.ExchangeRatesItem
import com.example.waluty.extensions.toExchangeRatesEntity
import com.example.waluty.extensions.toExchangeRatesItem
import com.example.waluty.usecase.database.DatabaseUseCase
import com.example.waluty.usecase.exchangesdata.ExchangesDataUseCase
import com.example.waluty.usecase.storedata.DataStoreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val downloadDataUseCase: ExchangesDataUseCase,
    private val dataStoreUseCase: DataStoreUseCase,
    private val databaseUseCase: DatabaseUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val TAG = "HomeViewModel"

    private val _exchangeRates = MutableLiveData<SnapshotStateList<ExchangeRatesItem>>()
    val exchangeRates: LiveData<SnapshotStateList<ExchangeRatesItem>>
        get() = _exchangeRates
    private val exchangeRatesImpl = mutableStateListOf<ExchangeRatesItem>()

    private val _snackbarData = MutableLiveData<SnackbarData>()
    val snackbarData: LiveData<SnackbarData> get() = _snackbarData

    // Gets exchange rates and notifies to exchangeRates field.
    suspend fun getExchangeRates() {
        Log.d(TAG, "getExchangeRates() called")

        withContext(Dispatchers.Default) {
            // First, take data from DB and display it only if not empty
            getExchangesFromDB()

            // Second, if result is Success and data is unique:
            // - clears exchange rates table, populate it with new data
            // - updates list with new exchange rates data
            getExchangesFromNetwork()
        }
    }

    private suspend fun getExchangesFromDB() {
        Log.d(TAG, "getExchangesFromDB() called")

        withContext(Dispatchers.Default) {
            val exchangeRatesEntity = databaseUseCase.getExchangeRatesEntity()
            val exchangeRatesItem = exchangeRatesEntity?.toExchangeRatesItem()

            exchangeRatesItem?.let {
                Log.d(TAG, "getExchangeRates: found data in DB")

                if (it == exchangeRatesImpl.firstOrNull()) {
                    Log.d(TAG, "getExchangesFromDB: database data same as existing, skipping")
                    return@let
                }

                _exchangeRates.postValue(
                    // This way we can inform that data has been changed
                    exchangeRatesImpl.apply {
                        clear()
                        add(0, it)
                    }
                )
            } ?: run {
                Log.d(TAG, "getExchangeRates: no data in DB")
            }
        }
    }

    private suspend fun getExchangesFromNetwork() {
        Log.d(TAG, "getExchangesFromNetwork() called")

        withContext(Dispatchers.Default) {
            val exchangeRates = downloadDataUseCase.getResultExchangeRates()
            Log.d(TAG, "getResultExchangeRates: $exchangeRates")

            when (exchangeRates) {
                is Result.NoInternetConnection -> {
                    _snackbarData.postValue(SnackbarData.NoInternetConnection_TryAgain)
                }

                is Result.Success -> {
                    val data = exchangeRates.data

                    // REST returns result in table, we must take first element and work on it
                    val exchangeRateItem = data.firstOrNull()

                    exchangeRateItem?.let {
                        Log.d(TAG, "getResultExchangeRates: obtained data from REST")

                        if (it == exchangeRatesImpl.firstOrNull()) {
                            Log.d(
                                TAG,
                                "getExchangesFromDB: network data same as existing, skipping"
                            )
                            return@let
                        }

                        // Clear data in database
                        Log.d(TAG, "deleteExchangeRates: cleaning old data")
                        databaseUseCase.deleteExchangeRates()

                        // Insert new data into database
                        Log.d(TAG, "insertExchangeRates: populating with new data")
                        databaseUseCase.insertExchangeRates(
                            it.toExchangeRatesEntity()
                        )

                        _exchangeRates.postValue(
                            // This way we can inform that data has been changed
                            exchangeRatesImpl.apply {
                                clear()
                                add(0, it)
                            }
                        )
                    }
                }

                else -> {
                    _snackbarData.postValue(SnackbarData.GeneralError)
                }
            }
        }
    }

    fun addFavouriteCurrency(code: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                dataStoreUseCase.addFavouriteCurrency(code)
            }
        }
    }

    fun removeFavouriteCurrency(code: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                dataStoreUseCase.removeFavouriteCurrency(code)
            }
        }
    }

    fun isFavouriteCurrency(code: String) = runBlocking {
        return@runBlocking dataStoreUseCase.isFavouriteCurrency(code)
    }

    /**
     * Returns user's name.
     */
    fun getName() = runBlocking {
        return@runBlocking dataStoreUseCase.getName()
    }

    fun setSnackbarDataNoInternetConnection() {
        _snackbarData.postValue(SnackbarData.NoInternetConnection)
    }

    fun setSnackbarDataHide() {
        _snackbarData.value = SnackbarData.Hide
    }

    fun checkNetwork(onAvailable: () -> Unit, onNotAvailable: () -> Unit) {
        if (networkChecker.isNetworkAvailable()) {
            onAvailable.invoke()
        } else
            onNotAvailable.invoke()
    }

}