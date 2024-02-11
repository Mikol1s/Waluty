package com.example.waluty.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TitleIconBar(text: String, imageVector: ImageVector, onIconClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = text)
        },
        actions = {
            // Add any additional actions, such as an icon
            IconButton(onClick = {
                onIconClick.invoke()
            }) {
                Icon(imageVector, contentDescription = null)
            }
        }
    )
}