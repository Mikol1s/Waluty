package com.example.waluty.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleSnackbar(
    actionText: String,
    message: String,
    onClick: () -> Unit
) {
    Snackbar(
        modifier = Modifier.padding(16.dp),
        action = {
            TextButton(
                onClick = {
                    onClick.invoke()
                }
            ) {
                Text(actionText)
            }
        },
        content = {
            Text(message)
        }
    )
}