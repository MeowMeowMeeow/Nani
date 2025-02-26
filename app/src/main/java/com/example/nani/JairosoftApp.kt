package com.example.nani

import android.content.res.Configuration
import androidx.compose.ui.unit.dp

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nani.screens.DashboardGroup
import com.example.nani.screens.ForgotPasswordScreen
import com.example.nani.screens.LoginGroup
import com.example.nani.ui.theme.NaNiTheme

enum class JairosoftAppScreen(@StringRes val title: Int) {
    Initiial(title = R.string.app_name),
    Forgot(title = R.string.forgot_password),
    Signup(title = R.string.signup),
    Dashboard(title = R.string.lblDashboard)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JairosoftApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = JairosoftAppScreen.valueOf(
        backStackEntry?.destination?.route ?: JairosoftAppScreen.Initiial.name
    )

    Scaffold(
        bottomBar = {
            if (currentScreen == JairosoftAppScreen.Dashboard) {
                JairosoftAppBar()
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = JairosoftAppScreen.Initiial.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = JairosoftAppScreen.Initiial.name) {
                LoginGroup(
                    onForgotPassword = {
                        navController.navigate(JairosoftAppScreen.Forgot.name)
                    },
                    onLogin = {
                        navController.navigate(JairosoftAppScreen.Dashboard.name)
                    }
                )
            }

            composable(route = JairosoftAppScreen.Forgot.name) {
                ForgotPasswordScreen(navController)
            }

            composable(route = JairosoftAppScreen.Dashboard.name) {
                DashboardGroup()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JairosoftAppBar() {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(80.dp),
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                tonalElevation = 8.dp,
                contentPadding = PaddingValues(horizontal = 10.dp),
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,

                    ) {
                        Row {
                            Spacer(modifier = Modifier.width(5.dp))
                            BottomBarIcons(R.drawable.dashboard, "Dashboard")
                            Spacer(modifier = Modifier.padding(5.dp))
                            BottomBarIcons(R.drawable.analytics, "Analytics")
                        }
                        Spacer(modifier = Modifier.padding(40.dp))
                        Row {
                            BottomBarIcons(R.drawable.projects, "Projects")
                            Spacer(modifier = Modifier.padding(10.dp))
                            BottomBarIcons(R.drawable.profile, "Profile")

                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* FAB Action */ },
                shape = CircleShape,
                containerColor = Color.Green,
                elevation = FloatingActionButtonDefaults.elevation(12.dp),
                modifier = Modifier
                    .size(80.dp)
                    .offset(y = 50.dp, x = 8.dp) // Moves ang Cicle


            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            }

        },
        floatingActionButtonPosition = FabPosition.Center
    )     { innerPadding -> Box(modifier = Modifier.padding(innerPadding)) { } }

}



@Composable
fun BottomBarIcons(iconRes: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)

        )
        Text(text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface)
    }
}



@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Appbarpreview()
{
    NaNiTheme {
        JairosoftAppBar()
    }
}




