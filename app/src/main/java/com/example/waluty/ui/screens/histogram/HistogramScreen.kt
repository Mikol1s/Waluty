package com.example.waluty.ui.screens.histogram

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.waluty.composables.LineChart
import com.example.waluty.composables.StatsCardDateStartEnd
import com.example.waluty.composables.StatsCardMinMaxMed
import com.example.waluty.composables.StatsCardSelectedDate
import com.example.waluty.composables.TitleIconBar
import com.example.waluty.data.exchangeRatesRates.ExchangeRatesRatesItem
import com.example.waluty.data.exchangeRatesRates.Rates
import com.example.waluty.extensions.exchangesDataUseCase
import com.example.waluty.utils.convertDateFormat
import com.example.waluty.utils.toDate
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Date

lateinit var histogramViewModel: HistogramViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistogramScreen(
    code: String?,
    currency: String?,
) {
    if (code == null || currency == null) {
        throw IllegalArgumentException("Variable 'code' is required!")
    }

    histogramViewModel = viewModel(
        factory = HistogramViewModelFactory(
            exchangesDataUseCase = LocalView.current.context.exchangesDataUseCase
        )
    )

    val defaultStartDate = Date().toDate(-14)
    val defaultEndDate = Date().toDate(0)

    var startDate by remember { mutableStateOf(defaultStartDate) }
    var endDate by remember { mutableStateOf(defaultEndDate) }

    val exchangeRatesRates by histogramViewModel.exchangeRatesRates.observeAsState(null)

    if (histogramViewModel.exchangeRatesRates.value == null) {
        histogramViewModel.getExchangeRatesRates(
            currency = code,
            startDate = startDate,
            endDate = endDate
        )
    }

    var tappedRates by remember { mutableStateOf<Rates?>(null) }

    var isDialogVisible by remember { mutableStateOf(false) }

    val datePicker = remember {
        MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select dates")
            .build().apply {
                addOnPositiveButtonClickListener { dateRange ->
                    startDate = Date(dateRange.first)
                    endDate = Date(dateRange.second)
                    isDialogVisible = false

                    histogramViewModel.getExchangeRatesRates(
                        currency = code,
                        startDate = startDate,
                        endDate = endDate
                    )
                }
                addOnCancelListener {
                    isDialogVisible = false
                }
            }
    }

    val fragmentActivity = LocalContext.current as? FragmentActivity

    if (isDialogVisible) {
        LaunchedEffect(datePicker) {
            fragmentActivity?.let {
                datePicker.show(it.supportFragmentManager, datePicker.toString())
            }
        }
    }

    Scaffold(
        topBar = {
            TitleIconBar(
                text = currency,
                imageVector = Icons.Default.CalendarMonth
            ) {
                isDialogVisible = true
            }
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                HistogramLineChartPadded(code, exchangeRatesRates) { rates ->
                    tappedRates = rates
                }
                //Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    StatsCardSelectedDate(tappedRates)
                }
                Spacer(modifier = Modifier.height(16.dp))
                StatsCardMinMaxMedPadded(exchangeRatesRates)
                Spacer(modifier = Modifier.height(16.dp))
                StatsCardDateStartEndPadded(startDate, endDate)
            }
        }
    )
}

@Composable
private fun StatsCardDateStartEndPadded(startDate: Date, endDate: Date) {
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        StatsCardDateStartEnd(
            dateStart = startDate,
            dateEnd = endDate
        )
    }
}

@Composable
private fun StatsCardMinMaxMedPadded(exchangeRatesRates: ExchangeRatesRatesItem?) {
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        val minValue = histogramViewModel.calcMinValue(exchangeRatesRates)
        val maxValue = histogramViewModel.calcMaxValue(exchangeRatesRates)
        val medianValue = histogramViewModel.calcMedianValue(
            minValue, maxValue
        )
        StatsCardMinMaxMed(
            minValue = minValue,
            medianValue = medianValue,
            maxValue = maxValue
        )
    }
}

@Composable
fun HistogramLineChartPadded(
    code: String, exchangeRatesRates: ExchangeRatesRatesItem?, onTap: (Rates?) -> Unit
) {
    Box(
        modifier = Modifier
            .requiredHeight(250.dp)
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        HistogramLineChart(
            code = code,
            exchangeRatesRates = exchangeRatesRates,
            onTap = onTap
        )
    }
}

@Composable
private fun HistogramLineChart(
    code: String,
    exchangeRatesRates: ExchangeRatesRatesItem?,
    onTap: ((Rates?) -> Unit)
) {
    val mapYValues: List<Float> = exchangeRatesRates?.rates?.map { it.mid }.orEmpty()
    val mapXValues: List<String> =
        exchangeRatesRates?.rates?.map {
            val effectiveDate = it.effectiveDate
            val convertDateFormat = effectiveDate.convertDateFormat("yyyy-MM-dd", "dd-MMM")
            convertDateFormat
        }.orEmpty()

    LineChart(
        lineLabel = code,
        xVals = mapXValues,
        yVals = mapYValues,
        onTap = onTap
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistogramScreenPreview() {
    rememberNavController()
    HistogramScreen(
        "NOK",
        "Korona norweska"
    )
}