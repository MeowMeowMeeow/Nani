package com.example.nani.ui.theme.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.nani.R
import com.example.nani.screens.Dashboard.JairosoftAppScreen


//The Weekly progress
@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    progress: Float
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = progress.coerceIn(0f, 0.8f))
                    .height(20.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .background(MaterialTheme.colorScheme.tertiaryContainer.copy(0.8f))
                    .animateContentSize()
            )
        }
    }
}

@Composable
@Preview
fun PreviewProgressBar() {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "M",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(15.dp))
            ProgressBar(progress = 0.3f) // Example progress
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "T",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(15.dp))
            ProgressBar(progress = 0.7f) // Example progress
        }
    }
}


//For navigation bar
@Composable
fun JairosoftAppBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 8.dp,
        contentPadding = PaddingValues(horizontal = 10.dp),
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Spacer(modifier = Modifier.width(5.dp))
                    BottomNavItem(
                        navController,
                        JairosoftAppScreen.Dashboard,
                        R.drawable.dashboard,
                        "Dashboard"
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    BottomNavItem(
                        navController,
                        JairosoftAppScreen.Analytics,
                        R.drawable.analytics,
                        "Analytics"
                    )
                }
                Spacer(modifier = Modifier.padding(40.dp))
                Row {
                    Spacer(modifier = Modifier.padding(start= 10.dp))
                    BottomNavItem(
                        navController,
                        JairosoftAppScreen.Projects,
                        R.drawable.projects,
                        "Projects"
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    BottomNavItem(
                        navController,
                        JairosoftAppScreen.Profile,
                        R.drawable.profile,
                        "Profile"
                    )
                }
            }
        }

    )

}

@Composable
fun BottomNavItem(navController: NavController, screen: JairosoftAppScreen, icon: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable {
                navController.navigate(screen.name) {
                    popUpTo(screen.name) { inclusive = true }
                }
            }

            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            colorFilter = bottomIconImageColor(navController, screen)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = bottomIconColor(navController, screen)
        )
    }
}

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




