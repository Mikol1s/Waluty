package com.example.waluty

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.waluty.api.ApiService
import com.example.waluty.api.RetrofitBuilder
import com.example.waluty.extensions.dataStore
import com.example.waluty.extensions.database
import com.example.waluty.extensions.networkChecker
import com.example.waluty.ui.screens.histogram.HistogramScreen
import com.example.waluty.ui.screens.home.HomeScreen
import com.example.waluty.ui.screens.settings.SettingsScreen
import com.example.waluty.ui.theme.WalutyTheme
import com.example.waluty.usecase.database.DatabaseRepositoryImpl
import com.example.waluty.usecase.database.DatabaseUseCase
import com.example.waluty.usecase.exchangesdata.ExchangesDataRepositoryImpl
import com.example.waluty.usecase.exchangesdata.ExchangesDataUseCase
import com.example.waluty.usecase.storedata.DataStoreUseCase
import com.example.waluty.usecase.storedata.StoreDataRepositoryImpl
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var isDarkTheme = mutableStateOf(false)

    val dataStoreUseCase by lazy {
        DataStoreUseCase( // zapisuje uzytkownika
            storeDataRepository = StoreDataRepositoryImpl(
                dataStore = this.dataStore
            )
        )
    }

    val exchangesDataUseCase by lazy {
        ExchangesDataUseCase(
            networkChecker = this.networkChecker,
            fetchDataRepository = ExchangesDataRepositoryImpl(
                apiService = RetrofitBuilder.retrofit.create(ApiService::class.java),
            )
        )
    }

    val databaseUseCase by lazy { // inicjalizacja w momencie pierwszego odwołania
        DatabaseUseCase(
            databaseRepository = DatabaseRepositoryImpl( // repozytoruym (pozyskuje dane)
                database = this.database
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            isDarkTheme.value = dataStoreUseCase.isDarkMode()

            setContent {
                WalutyTheme(darkTheme = isDarkTheme.value) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()

                        NavHost(
                            navController = navController,
                            startDestination = "homeScreen"
                        ) {
                            composable("homeScreen") {
                                HomeScreen(
                                    navigateToSettings = {
                                        navController.navigate("settings")
                                    },
                                    navigateToHistogram = { code, currency ->
                                        navController.navigate(
                                            "histogramScreen/$code/$currency"
                                        )
                                    },
                                )
                            }
                            composable(
                                route = "histogramScreen/{code}/{currency}",
                                arguments = listOf(
                                    navArgument("code") { type = NavType.StringType },
                                )
                            ) { backStackEntry ->
                                HistogramScreen(
                                    backStackEntry.arguments?.getString("code"),
                                    backStackEntry.arguments?.getString("currency"),
                                )
                            }
                            composable("settings") {
                                SettingsScreen()
                            }
                        }
                    }
                }
            }
        }
    }

    fun setDarkMode(checked: Boolean) {
        // Used to notify theme (up)
        isDarkTheme.value = checked

        // Used to save information
        lifecycleScope.launch {
            dataStoreUseCase.setDarkMode(checked)
        }
    }
}


