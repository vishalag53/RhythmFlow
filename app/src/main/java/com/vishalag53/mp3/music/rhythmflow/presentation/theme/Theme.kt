package com.vishalag53.mp3.music.rhythmflow.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import com.vishalag53.mp3.music.rhythmflow.domain.core.K

private val greenLightScheme = lightColorScheme(
    primary = greenPrimaryLight,
    secondary = greenSecondaryLight,
    tertiary = greenTertiaryLight,
    primaryContainer = greenContainerLight,
    error = greenErrorLight,
    errorContainer = greenErrorContainerLight,
    background = greenBackgroundLight,
    surface = greenBackgroundLight,
    onBackground = greenBackgroundDark
)

private val greenDarkScheme = darkColorScheme(
    primary = greenPrimaryDark,
    secondary = greenSecondaryDark,
    tertiary = greenTertiaryDark,
    primaryContainer = greenContainerDark,
    error = greenErrorDark,
    errorContainer = greenErrorContainerDark,
    background = greenBackgroundDark,
    surface = greenBackgroundDark,
    onBackground = greenBackgroundLight
)

private val blueLightScheme = lightColorScheme(
    primary = bluePrimaryLight,
    secondary = blueSecondaryLight,
    tertiary = blueTertiaryLight,
    primaryContainer = blueContainerLight,
    error = blueErrorLight,
    errorContainer = blueErrorContainerLight,
    background = blueBackgroundLight,
    surface = blueBackgroundLight,
    onBackground = blueBackgroundDark
)

private val blueDarkScheme = darkColorScheme(
    primary = bluePrimaryDark,
    secondary = blueSecondaryDark,
    tertiary = blueTertiaryDark,
    primaryContainer = blueContainerDark,
    error = blueErrorDark,
    errorContainer = blueErrorContainerDark,
    background = blueBackgroundDark,
    surface = blueBackgroundDark,
    onBackground = blueBackgroundLight
)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RhythmFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val themeColor = K.THEME_BLUE
    var colorScheme = if (darkTheme) blueDarkScheme else blueLightScheme

    when (themeColor) {
        K.THEME_BLUE -> colorScheme = if (darkTheme) blueDarkScheme else blueLightScheme
        K.THEME_GREEN -> colorScheme = if (darkTheme) greenDarkScheme else greenLightScheme
        else -> colorScheme = if (darkTheme) blueDarkScheme else blueLightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}