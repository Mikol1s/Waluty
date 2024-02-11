package com.example.waluty.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.waluty.utils.toTimeFormat
import kotlinx.coroutines.delay
import java.util.Date

@Composable
fun CurrentTimeText() {
    var currentTime by remember { mutableStateOf(Date().toTimeFormat()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // Odczekaj 1 sekundę
            currentTime = Date().toTimeFormat() // Zaktualizuj czas
        }
    }

    Text(
        text = "Aktualna godzina: $currentTime",
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
@Preview(showBackground = true)
fun CurrentTimeTextPreview() {
    CurrentTimeText()
}