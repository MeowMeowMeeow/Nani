package com.example.nani.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = White,
    secondary = Dark_Lightblue ,
    background = Dark_blue,
    surfaceContainer = Dark_Gray,
    onPrimary = White,
    onSurface = Dark_Text,
    tertiary = White,


)

private val LightColorScheme = lightColorScheme(
    primary = Light_Title,
    surfaceContainer = Light_LightGray,
    secondary = Light_Blue,
    background = White,
    onPrimary = Light_Gray,
   onSurface = Light_Text,
    tertiary = White,



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