package com.example.waluty.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun StatsItem(imageVector: ImageVector, label: String?, value: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(IntrinsicSize.Min),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            imageVector = imageVector,
            contentDescription = null,
            tint = Color.White
        )
        label?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = label.orEmpty(),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        value?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = value,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}