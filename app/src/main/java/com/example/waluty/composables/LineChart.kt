package com.example.waluty.composables

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.example.waluty.data.exchangeRatesRates.Rates
import com.example.waluty.widgets.utils.DateValueFormatter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener


@Composable
fun LineChart(
    lineLabel: String,
    xVals: List<String>,
    yVals: List<Float>,
    onTap: ((Rates?) -> Unit)
) {
    val chartXAxisTextColor = MaterialTheme.colorScheme.onBackground.toArgb()
    val chartYAxisTextColor = MaterialTheme.colorScheme.onBackground.toArgb()
    val lineDataTextColor = MaterialTheme.colorScheme.primary.toArgb()
    val legendTextColor = MaterialTheme.colorScheme.onBackground.toArgb()

    AndroidView(
        modifier = Modifier
            .fillMaxSize(),
        factory = { context ->
            LineChart(context)
        },
        update = { chartView ->
            configureLineChart(
                chartView = chartView,
                lineLabel = lineLabel,
                xValues = xVals,
                yValues = yVals,
                chartXAxisTextColor = chartXAxisTextColor,
                chartYAxisTextColor = chartYAxisTextColor,
                lineDataTextColor = lineDataTextColor,
                legendTextColor = legendTextColor,
                onTap = onTap
            )
        }
    )
}

private fun configureLineChart(
    chartView: LineChart,
    lineLabel: String,
    xValues: List<String>,
    yValues: List<Float>,
    chartXAxisTextColor: Int,
    chartYAxisTextColor: Int,
    lineDataTextColor: Int,
    legendTextColor: Int,
    onTap: (Rates?) -> Unit
) {
    // Customize X-axis
    chartView.xAxis.apply {
        labelRotationAngle = -45f
        valueFormatter = DateValueFormatter(xValues)
        position = XAxis.XAxisPosition.BOTTOM
        textColor = chartXAxisTextColor
        setLabelCount(xValues.size.coerceAtMost(10), true)
    }

    // Customize Y-axis left
    chartView.axisLeft.apply {
        setLabelCount(5, true)

        textColor = chartYAxisTextColor
        gridColor = Color.GRAY
        gridLineWidth = 0.5f
    }

    // Customize Y-axis right
    chartView.axisRight.apply {
        isEnabled = false
    }

    chartView.description = Description().apply {
        text = ""
    }

    chartView.getLegend().apply {
        textColor = legendTextColor
    }

    chartView.setExtraOffsets(0f, 0f, 0f, 16f)

    chartView.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
        override fun onValueSelected(entry: Entry?, highlight: Highlight?) {
            entry?.let {
                val rates = Rates(
                    no = it.x.toInt().toString(),
                    effectiveDate = xValues[entry.x.toInt()],
                    mid = entry.y
                )
                onTap.invoke(rates)
            }
        }

        override fun onNothingSelected() {
            onTap.invoke(null)
        }
    })

    val entries = mutableListOf<Entry>()
    for ((index, value) in yValues.withIndex()) {
        entries.add(Entry(index.toFloat(), value))
    }

    val lineData = LineData(
        LineDataSet(entries, lineLabel).apply {
            setDrawFilled(true)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            color = lineDataTextColor
            lineWidth = 2f
            fillColor = Color.BLUE
            fillAlpha = 90
            highLightColor = Color.MAGENTA
            highlightLineWidth = 2f
            setDrawValues(false)
        })

    chartView.data = lineData

    chartView.invalidate()
}