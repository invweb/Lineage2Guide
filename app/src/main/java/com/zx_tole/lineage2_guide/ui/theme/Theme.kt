package com.zx_tole.lineage2_guide.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = L2Primary,
    primaryContainer = L2PrimaryContainer,
    secondary = L2Secondary,
    tertiary = L2Tertiary,
    error = L2Error,
    background = L2DarkBackground,
    surface = L2DarkSurface,
    surfaceVariant = L2DarkSurfaceVariant,
    surfaceContainer = L2DarkCard,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onError = Color.Black,
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFFE0E0E0),
    onSurfaceVariant = Color(0xFFB0B0C0),
    surfaceContainerHigh = L2DarkSurfaceVariant,
    surfaceContainerHighest = L2DarkSurfaceVariant
)

private val LightColorScheme = lightColorScheme(
    primary = L2LightPrimary,
    primaryContainer = L2LightPrimaryContainer,
    secondary = L2Secondary,
    tertiary = L2Tertiary,
    error = L2Error,
    background = L2LightBackground,
    surface = L2LightSurface,
    surfaceVariant = L2LightSurfaceVariant,
    surfaceContainer = L2LightCard,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onError = Color.Black,
    onBackground = Color(0xFF1A1A1A),
    onSurface = Color(0xFF1A1A1A),
    onSurfaceVariant = Color(0xFF4A4A5A),
    surfaceContainerHigh = L2LightSurfaceVariant,
    surfaceContainerHighest = L2LightSurfaceVariant
)

@Composable
fun Lineage2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
