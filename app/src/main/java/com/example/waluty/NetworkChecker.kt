package com.example.waluty

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.waluty.extensions.applicationContextSafe

class NetworkChecker(val context: Context) {

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.applicationContextSafe.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        if (connectivityManager != null) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        }
        return false
    }
}
