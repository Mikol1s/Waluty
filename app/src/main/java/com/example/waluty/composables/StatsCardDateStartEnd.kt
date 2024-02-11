package com.example.waluty.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.waluty.utils.toDateFormat
import java.util.Date

@Composable
fun StatsCardDateStartEnd(dateStart: Date, dateEnd: Date) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Icon Row
                Row(
                    modifier = Modifier
                        .weight(.33f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    StatsItem(
                        Icons.Default.EditCalendar,
                        "Od",
                        dateStart.toDateFormat("dd MMM yyyy")
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(.33f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    StatsItem(Icons.Default.SwapHoriz, null, null)
                }
                Row(
                    modifier = Modifier
                        .weight(.33f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    StatsItem(
                        Icons.Default.EditCalendar,
                        "Do",
                        dateEnd.toDateFormat("dd MMM yyyy")
                    )
                }
            }
        }
    }
}