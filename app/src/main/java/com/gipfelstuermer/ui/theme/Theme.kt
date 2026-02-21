package com.gipfelstuermer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = HeroGradientStart,
    onPrimary = TextOnPrimary,
    secondary = TabActiveGreen,
    onSecondary = TextOnPrimary,
    background = BackgroundWhite,
    onBackground = TextPrimary,
    surface = CardWhite,
    onSurface = TextPrimary,
    surfaceVariant = CardWhite,
    onSurfaceVariant = TextSecondary
)

@Composable
fun GipfelStuermerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
