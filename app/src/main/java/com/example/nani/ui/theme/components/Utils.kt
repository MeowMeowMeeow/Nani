package com.example.nani.ui.theme.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.nani.screens.Dashboard.JairosoftAppScreen


@Composable
fun bottomIconColor(navController: NavController, label: JairosoftAppScreen): Color {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    return if (currentRoute == label.name) MaterialTheme.colorScheme.secondary
    else MaterialTheme.colorScheme.primary
}

@Composable
fun bottomIconImageColor(navController: NavController, label: JairosoftAppScreen): ColorFilter? {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    return if (currentRoute == label.name) ColorFilter.tint(MaterialTheme.colorScheme.secondary)
    else ColorFilter.tint(MaterialTheme.colorScheme.primary)
}
