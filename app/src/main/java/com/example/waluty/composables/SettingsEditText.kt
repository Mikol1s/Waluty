package com.example.waluty.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsEditText(
    label: String,
    hint: String,
    value: String,
    onNameChanged: (String) -> Unit,
) {
    Header(label)
    Row(value, onNameChanged, hint)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Row(
    value: String,
    onNameChanged: (String) -> Unit,
    hint: String
) {
    var textRemembered by remember {
        mutableStateOf(
            TextFieldValue(value)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = textRemembered,
            modifier = Modifier
                .fillMaxWidth(),
            onValueChange = {
                textRemembered = it
                onNameChanged.invoke(it.text)
            },
            label = { Text(hint) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = androidx.compose.ui.text.input.ImeAction.Done
            )
        )
    }
}

@Composable
private fun Header(label: String) {
    val colorScheme = MaterialTheme.colorScheme

    Text(
        text = label,
        fontWeight = FontWeight.Bold,
        color = colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun SettingsEditTextPreview() {
    SettingsEditText(
        label = "Personalizacja",
        hint = "Twoje imię",
        value = "Mikołaj",
    ) {}
}
