package com.example.waluty.ui.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.waluty.MainActivity
import com.example.waluty.composables.SettingsCheckBox
import com.example.waluty.composables.SettingsEditText
import com.example.waluty.extensions.storeDataUseCase

private lateinit var settingsViewModel: SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    settingsViewModel = viewModel(
        factory = SettingsViewModelFactory(
            dataStoreUseCase = LocalView.current.context.storeDataUseCase
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ustawienia") },
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column {
                //Spacer(modifier = Modifier.height(16.dp))

                SettingsEditText(
                    label = "Personalizacja",
                    hint = "Twoje imię",
                    value = settingsViewModel.getName(),
                ) { onTextChanged ->
                    settingsViewModel.setName(onTextChanged)
                }

                val context = LocalContext.current
                val activity = context as? MainActivity

                SettingsCheckBox(
                    checked = settingsViewModel.isDarkMode(),
                    label = "Wygląd",
                    text = "Używaj ciemnego motywu",
                ) { checked ->
                    activity?.setDarkMode(checked)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StartScreenPreview() {
    SettingsScreen()
}