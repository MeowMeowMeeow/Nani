package com.example.nani.ui.theme.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//Separation of Concern para di kay gubot atong code

@Composable
fun BottomNavBarConditionals (){
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val landscape =  screenWidth in 801..900
    val isLargeScreen =  screenWidth in 601..800
    val tabletScreenL = screenWidth >= 901
    val tabletScreen = screenWidth >= 901


    @Composable
    fun MiddlePadding(middlePadding: String): Dp {
        return when
        {
            landscape -> 95.dp
            tabletScreenL ->250.dp
            isLargeScreen -> 40.dp
            else -> 10.dp
        }
    }

    @Composable
    fun Padding (padding :String): Dp {
        return when {
            tabletScreen -> 80.dp
            isLargeScreen -> 50.dp
            else -> 1.dp
        }
    }
}

@Composable
fun ProfileConditionals(){
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val landscape =  screenWidth in 801..900
    val isLargeScreen =  screenWidth in 601..800
    val tablet=  screenWidth in 901..1400
    val desktop = screenWidth >= 1401

    @Composable
    fun ArcOffset (arcOffset : String): Dp{
        return when {
            landscape ->20.dp
            tablet -> 22.dp
            desktop->22.dp
            else -> 30.dp
        }

    }

    @Composable
    fun ImageOffset (imageOffset : String) : Dp {
        return when {
            tablet -> 160.dp
            landscape ->150.dp
            desktop -> 200.dp
            else -> 100.dp
        }
    }

    @Composable
    fun ImageSize (imageSize :String) : Dp {
        return when {
            landscape -> 200.dp
            tablet -> 220.dp
            desktop -> 250.dp
            else -> 150.dp
        }

    }

    @Composable
    fun BetweenSpace (betweenSpace: String): Dp{
        return when {
            tablet -> 170.dp
            landscape -> 170.dp
            desktop ->250.dp
            isLargeScreen -> 30.dp
            else -> 30.dp
        }
    }

    @Composable
    fun SizeCircular (sizeCircular :String): Dp{
        return when {
            tablet-> 250.dp
            landscape -> 150.dp
            desktop ->250.dp
            isLargeScreen -> 150.dp
            else -> 150.dp
        }
    }
    @Composable
    fun CardPadding(cardPadding: String): Dp{
        return when {
            tablet -> 100.dp
            landscape -> 50.dp
            desktop ->100.dp
            isLargeScreen -> 20.dp
            else -> 1.dp
        }
    }
    @Composable
    fun TextSize (textSize : String): TextStyle {
        return when {
            tablet-> MaterialTheme.typography.titleLarge
            desktop -> MaterialTheme.typography.displayLarge
            else -> MaterialTheme.typography.titleMedium
        }
    }
    @Composable
    fun Offset (offset : String): Dp {
        return when {
            tablet ->  -140.dp
            desktop ->  -150.dp
            else -> -83.dp
        }
    }

}

