package com.example.waluty.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.waluty.data.exchangeRates.Rate
import com.example.waluty.enums.ViewState
import com.example.waluty.ui.screens.home.HomeViewModel
import kotlinx.coroutines.launch

private val TAG = "ExchangeRatesList"

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ExchangeRatesList(
    homeViewModel: HomeViewModel,
    filterByQueriedPhrase: String,
    onListItemClick: ((Rate, Int) -> Unit)
) {
    Log.d(TAG, "ExchangeRatesList: ")

    val exchangeRates by homeViewModel.exchangeRates.observeAsState(null)
    val rateList = exchangeRates?.firstOrNull()?.rates.orEmpty()

    if (rateList.isEmpty()) {
        OnRateListEmpty(homeViewModel)
    } else {
        OnRateListNotEmpty(homeViewModel, rateList, filterByQueriedPhrase, onListItemClick)
    }
}

@Composable
private fun OnRateListNotEmpty(
    homeViewModel: HomeViewModel,
    rateList: List<Rate>,
    filterByQueriedPhrase: String,
    onListItemClick: (Rate, Int) -> Unit
) {
    Log.d(TAG, "OnRateList: ")

    val sortedRateList = rateList
        .sortedWith(compareBy(
            // First, sort by whether the currency is a favorite or not
            { homeViewModel.isFavouriteCurrency(it.code).not() },
            // Then, sort by currency code
            { it.code }
        ))
        .filter {
            if (filterByQueriedPhrase.isEmpty()) {
                true
            } else {
                it.code.startsWith(prefix = filterByQueriedPhrase)
            }
        }

    LazyColumn(
        modifier = Modifier.fillMaxHeight()
    ) {
        items(sortedRateList.size) { index ->
            val onItemClickInternal: (Rate, Int) -> Unit = { rateClick, indexClick ->
                onListItemClick
                    .invoke(rateClick, indexClick)
            }

            val onIconClickInternal: (Rate, Boolean) -> Unit = { rateClick, favourite ->
                if (favourite) {
                    homeViewModel.addFavouriteCurrency(rateClick.code)
                } else {
                    homeViewModel.removeFavouriteCurrency(rateClick.code)
                }
            }

            ExchangeRatesItem(
                index = index,
                rate = sortedRateList[index],
                size = sortedRateList.size,
                onItemClick = onItemClickInternal,
                onIconClick = onIconClickInternal,
                favourite = homeViewModel.isFavouriteCurrency(sortedRateList[index].code)
            )
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun OnRateListEmpty(
    homeViewModel: HomeViewModel
) {
    Log.d(TAG, "OnEmptyRateList: ")

    var exchangeRatesState by remember { mutableStateOf(ViewState.Idle) }
    val coroutine = rememberCoroutineScope()

    if (exchangeRatesState != ViewState.Loading) {
        exchangeRatesState = ViewState.Loading

        LazyColumn(
            modifier = Modifier.fillMaxHeight()
        ) {
            items(10) { index ->
                MyShimmer(
                    index = index,
                    size = 10
                )
            }
        }

        coroutine.launch {
            homeViewModel.getExchangeRates()
        }
    }
}

