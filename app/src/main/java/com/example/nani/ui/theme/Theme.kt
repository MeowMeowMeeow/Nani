package com.example.nani.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = White,
    secondary = Dark_Lightblue ,
    background = Dark_blue,
    surfaceContainer = Dark_Gray,
    onPrimary = White,
    onSurface = Dark_Text,
    tertiary = White,
    onSecondary =Dark_bar,
    onError = Dark_Error,
    onSecondaryContainer = Dark_icon,
    surfaceVariant = Light_LightGray,
    surfaceDim = Dark_background,
    secondaryContainer = Dark_Gray,
    onSurfaceVariant =  Dark_Lightblue,
    tertiaryContainer = DarkGreen,
    inverseOnSurface = Dark,
    primaryContainer = Dark_pbar,
    onPrimaryContainer = Dark_Pbar,


)

private val LightColorScheme = lightColorScheme(
    primary = Light_Title,
    surfaceContainer = Light_LightGray,
    secondary = Light_Blue,
    background = White,
    onPrimary = Light_Gray,
   onSurface = Light_Text,
    tertiary = White,
    onSecondary = White_bar,
    onError = White_Error,
    onSecondaryContainer = Gray,
    surfaceVariant = Dark_icon,
    surfaceDim = Light_background,
    secondaryContainer = White,
    onSurfaceVariant = blue,
    tertiaryContainer = LightGreen,
    inverseOnSurface = llightblue,
    primaryContainer = Light_pbar,
    onPrimaryContainer = Light_Pbar




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

@Composable
fun NaNiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = CustomTypography,
        content = content
    )
}