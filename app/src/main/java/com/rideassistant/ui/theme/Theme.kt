package com.rideassistant.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF1f97d4),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFd4e7f7),
    onPrimaryContainer = Color(0xFF003d66),
    secondary = Color(0xFF0066cc),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFcce5ff),
    onSecondaryContainer = Color(0xFF001f4d),
    tertiary = Color(0xFFffc107),
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFFffe082),
    onTertiaryContainer = Color(0xFF664d00),
    error = Color(0xFFf44336),
    onError = Color.White,
    errorContainer = Color(0xFFf8ccc8),
    onErrorContainer = Color(0xFF660000),
    background = Color(0xFFFAFAFA),
    onBackground = Color(0xFF1a1a1a),
    surface = Color.White,
    onSurface = Color(0xFF1a1a1a),
    surfaceVariant = Color(0xFFf0f0f0),
    onSurfaceVariant = Color(0xFF666666),
    outline = Color(0xFF999999)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF64b5f6),
    onPrimary = Color(0xFF003d66),
    primaryContainer = Color(0xFF005a99),
    onPrimaryContainer = Color(0xFFd4e7f7),
    secondary = Color(0xFF64b5f6),
    onSecondary = Color(0xFF001f4d),
    secondaryContainer = Color(0xFF0047a3),
    onSecondaryContainer = Color(0xFFcce5ff),
    tertiary = Color(0xFFffe082),
    onTertiary = Color(0xFF664d00),
    tertiaryContainer = Color(0xFFffa500),
    onTertiaryContainer = Color(0xFF2d2000),
    error = Color(0xFFef5350),
    onError = Color(0xFF660000),
    errorContainer = Color(0xFFb71c1c),
    onErrorContainer = Color(0xFFf8ccc8),
    background = Color(0xFF121212),
    onBackground = Color(0xFFe0e0e0),
    surface = Color(0xFF1e1e1e),
    onSurface = Color(0xFFe0e0e0),
    surfaceVariant = Color(0xFF2a2a2a),
    onSurfaceVariant = Color(0xFF999999),
    outline = Color(0xFF666666)
)

@Composable
fun RideAssistantTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
