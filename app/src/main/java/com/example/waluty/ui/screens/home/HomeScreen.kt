package com.example.waluty.ui.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.waluty.composables.CurrentTimeText
import com.example.waluty.composables.ExchangeRatesList
import com.example.waluty.composables.GreetingHeader
import com.example.waluty.composables.SearchBar
import com.example.waluty.composables.SimpleSnackbar
import com.example.waluty.composables.TopAppBarWithButton
import com.example.waluty.data.exchangeRates.Rate
import com.example.waluty.extensions.databaseUseCase
import com.example.waluty.extensions.exchangesDataUseCase
import com.example.waluty.extensions.networkChecker
import com.example.waluty.extensions.storeDataUseCase
import kotlinx.coroutines.launch

private lateinit var homeViewModel: HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToSettings: () -> Unit,
    navigateToHistogram: (String, String) -> Unit
) {
    homeViewModel = viewModel(
        factory = HomeViewModelFactory(
            exchangesDataUseCase = LocalView.current.context.exchangesDataUseCase,
            dataStoreUseCase = LocalView.current.context.storeDataUseCase,
            databaseUseCase = LocalView.current.context.databaseUseCase,
            networkChecker = LocalView.current.context.networkChecker
        )
    )

    val coroutine = rememberCoroutineScope()

    // while decomposing - it will be used to filter existing items list by provided phrase
    var queriedPhrase by remember { mutableStateOf("") }

    // Notified when http client returns result of fetched data
    val snackbarDataState = homeViewModel.snackbarData.observeAsState(SnackbarData.Idle)

    // Decides if data should be fetched again - Snackbar click action "try again"
    var needToReload by remember { mutableStateOf(false) }

    DisposableEffect(needToReload) {
        if (needToReload) {
            needToReload = false

            coroutine.launch {
                homeViewModel.getExchangeRates()// suspend function
            }
        }

        onDispose {}
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBarWithButton(navigateToSettings)
                GreetingHeaderPadded(homeViewModel.getName())
                SearchBar { queriedPhrase = it }
            }
        },
        snackbarHost = {
            when (snackbarDataState.value) {
                SnackbarData.NoInternetConnection -> {
                    SimpleSnackbar(
                        actionText = "Rozumiem",
                        message = "Jesteś offline. Aby otworzyć, upewnij się, że masz aktywne połączenie internetowe.",
                        onClick = {
                            homeViewModel.setSnackbarDataHide()
                        }
                    )
                }

                SnackbarData.NoInternetConnection_TryAgain -> {
                    SimpleSnackbar(
                        actionText = "Ponów",
                        message = "Jesteś offline. Sprawdź połączenie internetowe!",
                        onClick = {
                            // Changes snackbarDataState to Hide
                            homeViewModel.setSnackbarDataHide()
                            // Will request to fetch data again
                            needToReload = true
                        }
                    )
                }

                SnackbarData.GeneralError -> {
                    SimpleSnackbar(
                        actionText = "Ponów próbę",
                        message = "Napotkano nieznay błąd",
                        onClick = {
                            homeViewModel.setSnackbarDataHide()
                            needToReload = true
                        }
                    )
                }

                SnackbarData.Hide -> {}
                SnackbarData.Idle -> {}
            }

        }
    ) { paddingValues ->
        Log.d("HomeScreen", "Scaffold content")
        BottomContent(
            paddingValues = paddingValues,
            filterByQueriedPhrase = queriedPhrase,
            onListItemClick = { rateItem, _ ->
                homeViewModel.checkNetwork(
                    onAvailable = {
                        navigateToHistogram(rateItem.code, rateItem.currency)
                    },
                    onNotAvailable = {
                        homeViewModel.setSnackbarDataNoInternetConnection()
                    }
                )
            }
        )
    }
}

@Composable
private fun BottomContent(
    paddingValues: PaddingValues,
    filterByQueriedPhrase: String,
    onListItemClick: ((Rate, Int) -> Unit)
) {
    Box(modifier = Modifier.padding(paddingValues)) {
        Column {
            TimeTextPadded() // Shows composable time
            Spacer(modifier = Modifier.height(16.dp)) // Adds composable space
            ExchangeRatesList( // Shows composable list
                homeViewModel = homeViewModel,
                filterByQueriedPhrase = filterByQueriedPhrase,
                onListItemClick = onListItemClick
            )
        }
    }
}

@Composable
private fun TimeTextPadded() {
    Box(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        CurrentTimeText()
    }
}

@Composable
private fun GreetingHeaderPadded(name: String) {
    Box(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        GreetingHeader(name)
    }
}