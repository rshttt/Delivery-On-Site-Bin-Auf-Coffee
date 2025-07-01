package com.example.deliveryonsitebinaufcoffee.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = BaseColor1,
    secondary = BaseColor2,
    tertiary = BaseColor3,
    outline = BaseColor4
)

private val LightColorScheme = lightColorScheme(
    primary = BaseColor1,
    secondary = BaseColor2,
    tertiary = BaseColor3,
    outline = BaseColor4
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}