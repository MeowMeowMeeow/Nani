package com.example.nani.ui.theme.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//Separation of Concern para di kay gubot atong code

    @Composable
    fun middlePadding(): Dp {
        val screenWidth = LocalConfiguration.current.screenWidthDp
        val tabletP = screenWidth in 501..600
        val landscape =  screenWidth in 801..900
        val isLargeScreen =  screenWidth in 601..800
        val tablet=  screenWidth in 901..1400
        return when
        {
            tabletP -> 10.dp
            landscape -> 80.dp
            tablet ->250.dp
            isLargeScreen -> 40.dp
            else -> 10.dp
        }
    }

    @Composable
    fun padding (): Dp {
        val screenWidth = LocalConfiguration.current.screenWidthDp
        val tabletP = screenWidth in 501..600
        val landscape =  screenWidth in 801..900
        val isLargeScreen =  screenWidth in 601..800
        val tablet=  screenWidth in 901..1400
        return when {
            tabletP -> 20.dp
            landscape -> 50.dp
            tablet -> 80.dp
            isLargeScreen -> 50.dp
            else -> 1.dp
        }
    }

    @Composable
    fun arcOffset (): Dp{
        val screenWidth = LocalConfiguration.current.screenWidthDp
        val landscape =  screenWidth in 801..900
        val tablet=  screenWidth in 901..1400
        val desktop = screenWidth >= 1401
        return when {

            landscape ->24.dp
            tablet -> 22.dp
            desktop->22.dp
            else -> 30.dp
        }
    }

    @Composable
    fun imageOffset () : Dp {
        val screenWidth = LocalConfiguration.current.screenWidthDp

        val landscape =  screenWidth in 801..900
        val tablet=  screenWidth in 901..1400
        val desktop = screenWidth >= 1401
        return when {
            tablet -> 160.dp
            landscape ->150.dp
            desktop -> 200.dp
            else -> 100.dp
        }
    }

    @Composable
    fun imageSize () : Dp {
        val screenWidth = LocalConfiguration.current.screenWidthDp

        val landscape =  screenWidth in 801..900
        val tablet=  screenWidth in 901..1400
        val desktop = screenWidth >= 1401
        return when {
            landscape -> 200.dp
            tablet -> 220.dp
            desktop -> 250.dp
            else -> 150.dp
        }

    }

    @Composable
    fun betweenSpace (): Dp{
        val screenWidth = LocalConfiguration.current.screenWidthDp

        val landscape =  screenWidth in 801..900
        val isLargeScreen =  screenWidth in 601..800
        val tablet=  screenWidth in 901..1400
        val desktop = screenWidth >= 1401
        return when {
            tablet -> 170.dp
            landscape -> 170.dp
            desktop ->250.dp
            isLargeScreen -> 30.dp
            else -> 30.dp
        }
    }

    @Composable
    fun sizeCircular (): Dp{
        val screenWidth = LocalConfiguration.current.screenWidthDp

        val landscape =  screenWidth in 801..900
        val isLargeScreen =  screenWidth in 601..800
        val tablet=  screenWidth in 901..1400
        val desktop = screenWidth >= 1401
        return when {
            tablet-> 250.dp
            landscape -> 150.dp
            desktop ->250.dp
            isLargeScreen -> 150.dp
            else -> 150.dp
        }
    }
    @Composable
    fun cardPadding(): Dp{
        val screenWidth = LocalConfiguration.current.screenWidthDp

        val landscape =  screenWidth in 801..900
        val isLargeScreen =  screenWidth in 601..800
        val tablet=  screenWidth in 901..1400
        val desktop = screenWidth >= 1401
        return when {
            tablet -> 100.dp
            landscape -> 50.dp
            desktop ->100.dp
            isLargeScreen -> 20.dp
            else -> 1.dp
        }
    }
    @Composable
    fun textSize (): TextStyle {
        val screenWidth = LocalConfiguration.current.screenWidthDp

        val tablet=  screenWidth in 901..1400
        val desktop = screenWidth >= 1401
        return when {
            tablet-> MaterialTheme.typography.titleLarge
            desktop -> MaterialTheme.typography.displayLarge
            else -> MaterialTheme.typography.titleMedium
        }
    }
    @Composable
    fun offset (): Dp {
        val screenWidth = LocalConfiguration.current.screenWidthDp
        val tablet=  screenWidth in 901..1400
        val desktop = screenWidth >= 1401
        return when {
            tablet ->  -(140.dp)
            desktop ->  -(150.dp)
            else -> -(83.dp)
        }
    }

@Composable
fun tablePadding(): Dp {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val tablet=  screenWidth in 901..1400
    val desktop = screenWidth >= 1401
    return when {
        tablet -> 80.dp
        desktop -> 150.dp
        else -> 8.dp
    }
}

//Projects screen
@Composable
fun colorPicked(title:String ): Color {
    val isDarkMode = isSystemInDarkTheme()
   return when (title) {
        "In Progress" -> if(isDarkMode) Color(0xFFDAD7FF).copy(alpha=0.4f) else Color(0xFFDAD7FF)
        "To Do" -> if (isDarkMode) Color(0xFFFFF2D7).copy(alpha=0.4f) else Color(0xFFFFF2D7)
        else -> if (isDarkMode) Color(0xFFD7FFDA).copy(alpha=0.4f) else Color(0xFFD7FFDA)
    }
}


