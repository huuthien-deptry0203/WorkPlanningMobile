package com.example.workplanning.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val DarkColorScheme = darkColorScheme(
    primary = textDark,
    secondary = buttondark,
    background = nendark,
    surface = xamden,
//    onPrimary = SpotifyTextDark,
//    onSecondary = SpotifyTextDark,
    onBackground = nenDonedark,
    onErrorContainer = nenOverduedark,
//    onSurface = ,
    error = errordark,
    onTertiary = dautichdark
    )

private val LightColorScheme = lightColorScheme(
    primary = textlight,
    secondary = buttonlight,
    background = nenlight,
    surface = xamtrang,
//    onPrimary = SpotifyTextLight,
//    onSecondary = SpotifyTextLight,
    onBackground = nenDonelight,
    onErrorContainer = nenOverduelight,
//    onSurface = ,
    error = errorlight,
    onTertiary = dautichlight
    )

@Composable
fun WorkPlanningTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
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
