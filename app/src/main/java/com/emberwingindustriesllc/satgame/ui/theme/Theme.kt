package com.emberwingindustriesllc.satgame.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val RetroDarkColorScheme = darkColorScheme(
    primary = GbaPrimary,
    onPrimary = GbaOnPrimary,
    primaryContainer = RetroShellAccent,
    secondary = GbaSecondary,
    onSecondary = Color.White,
    background = GbaBackground,
    onBackground = GbaOnSurface,
    surface = GbaSurface,
    onSurface = GbaOnSurface,
    surfaceVariant = GbaSurfaceVariant,
    outline = GbaOutline
)

private val RetroLightColorScheme = lightColorScheme(
    primary = GbaPrimary,
    onPrimary = GbaOnPrimary,
    primaryContainer = GbaPrimaryContainer,
    secondary = GbaSecondary,
    onSecondary = Color.White,
    background = Color(0xFFF1F5F9),
    onBackground = Color(0xFF0F172A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFE2E8F0),
    outline = Color(0xFF94A3B8)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent retro theme by default
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> RetroDarkColorScheme
        else -> RetroDarkColorScheme // Retro dark GBA console look is iconic
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
