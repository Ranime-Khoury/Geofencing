package com.example.geofencing.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorScheme = darkColorScheme(
    primary = Color(0xFF15193f),
    secondary = Color(0xFF3c8b92),
    onPrimary = Color.White,
    onSecondary = Color(0xFFd5e8f2)
)

@Composable
fun GeofencingTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = ColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}