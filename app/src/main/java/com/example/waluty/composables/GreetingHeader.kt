package com.example.waluty.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun GreetingHeader(name: String) {
    val text = if (name.isEmpty()) {
        "!"
    } else {
        ", $name!"
    }

    Text(
        text = "Witaj$text",
        style = MaterialTheme.typography.displayMedium
    )
}