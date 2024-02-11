package com.example.waluty.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Immutable
data class ExtendedColors(
    val circleLabel: Color,
    val statsBackground: Color,
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        circleLabel = Color.Unspecified,
        statsBackground = Color.Unspecified,
    )
}

// Default colors

private val LightColorScheme = lightColorScheme(
    primary = Color(0XFF1E88E5),
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0XFF0D47A1),
    secondary = PurpleGrey80,
    tertiary = Pink80,
    //background = Color(0xFF1f262B)
)

// Theming

@Composable
fun WalutyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                Log.d("WalutyTheme", "using dynamic dark color scheme")
                dynamicDarkColorScheme(context)
            } else {
                Log.d("WalutyTheme", "using dynamic light color scheme")
                dynamicLightColorScheme(context)
            }
        }

        darkTheme -> {
            Log.d("WalutyTheme", "using dark color scheme")
            DarkColorScheme
        }
        else -> {
            Log.d("WalutyTheme", "using light color scheme")
            LightColorScheme
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    // Extending colors
    val extendedColors = ExtendedColors(
        circleLabel = if (isSystemInDarkTheme()) {
            Color(0xFF0034B6)
        } else {
            Color(0xFF0034B6)
        },
        statsBackground = if (isSystemInDarkTheme()) {
            Color(0xFF0034B6)
        } else {
            Color(0xFF0034B6)
        },
    )

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

object WalutyTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}