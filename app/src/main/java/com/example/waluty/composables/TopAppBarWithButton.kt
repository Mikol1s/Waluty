package com.example.waluty.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarWithButton(navigateToSettings: () -> Unit) {
    TopAppBar(
        title = { Text("") },
        actions = {
            IconButton(onClick = {
                navigateToSettings.invoke()
            }) {
                Icon(Icons.Filled.Settings, contentDescription = null)
            }
        }
    )
}