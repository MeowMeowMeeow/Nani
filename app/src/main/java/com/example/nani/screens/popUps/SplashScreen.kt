package com.example.nani.screens.popUps

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.nani.R
import com.example.nani.JairosoftAppScreen
import com.example.nani.ui.theme.components.SessionManager
import kotlinx.coroutines.delay



@Composable
fun SplashScreen(navController: NavController) {
    val offsetY = remember { Animatable(160f) }
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.8f) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(2000)

        val currentUser = SessionManager.getUser(context)
        val token = SessionManager.getToken(context)

        if (!token.isNullOrEmpty() && currentUser != null) {
            navController.navigate(JairosoftAppScreen.Dashboard.name) {
                popUpTo(JairosoftAppScreen.SplashScreen.name) { inclusive = true }
            }
        } else {
            navController.navigate(JairosoftAppScreen.Login.name) {
                popUpTo(JairosoftAppScreen.SplashScreen.name) { inclusive = true }
            }
        }
    }
    LaunchedEffect(true) {
        alpha.animateTo(1f, animationSpec = tween(600))
        offsetY.animateTo(
            targetValue = 25f,
            animationSpec = tween(1200, easing = EaseOutBack)
        )
        scale.animateTo(1f, animationSpec = tween(500, easing = EaseOutQuad))

        delay(1000)
        navController.navigate(JairosoftAppScreen.Login.name) {
            popUpTo(JairosoftAppScreen.SplashScreen.name) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier.size(220.dp)
        ) {
            drawOval(
                color =Color.LightGray.copy(alpha=0.1f),
                size = Size(size.width, size.height / 3),
                topLeft = Offset(0f, size.height * 0.6f)
            )
        }


        Image(
            painter = painterResource(id = R.drawable.jairosoft),
            contentDescription = "Jairosoft Logo",
            modifier = Modifier
                .size(100.dp)
                .offset(y = offsetY.value.dp)

                .alpha(alpha.value)
                .scale(scale.value)
                .zIndex(1f)
        )

        Image(
            painter = painterResource(id = R.drawable.cutoubox),
            contentDescription = "Cutout Box",
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
            modifier = Modifier
                .offset(y = 115.dp, x=-(5.dp))
                .fillMaxWidth()
                .aspectRatio(2f)
                .zIndex(2f)

        )

    }
}
