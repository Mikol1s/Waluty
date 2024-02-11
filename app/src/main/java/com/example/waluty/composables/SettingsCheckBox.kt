package com.example.waluty.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsCheckBox(
    checked: Boolean,
    label: String,
    text: String,
    onChecked: (Boolean) -> Unit
) {
    Header(label)
    Row(text, checked, onChecked)
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
private fun Row(
    text: String,
    checked: Boolean,
    onChecked: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        var checkedRemembered by remember { mutableStateOf(checked) }

        Checkbox(
            checked = checkedRemembered,
            onCheckedChange = {
                checkedRemembered = it
                onChecked.invoke(it)
            },
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SettingsCheckBoxPreview(){
    SettingsCheckBox(true,"Wygląd","Użyj ciemnego motywu", {})
}
