package com.example.waluty.ui.screens.home

sealed class SnackbarData {
    data object NoInternetConnection_TryAgain : SnackbarData()
    data object NoInternetConnection : SnackbarData()
    data object GeneralError : SnackbarData()
    data object Hide: SnackbarData()
    data object Idle: SnackbarData()
}
