package com.example.nani.screens.Dashboard


import androidx.compose.foundation.layout.size


import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import android.content.res.Configuration
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nani.R
import com.example.nani.repository.LoginViewModelFactory
import com.example.nani.screens.Analytics.AnalyticsScreen
import com.example.nani.screens.PopUps.ForgotPasswordScreen
import com.example.nani.screens.Login.LoginGroup
import com.example.nani.screens.Login.LoginViewModel
import com.example.nani.screens.Profile.ProfileScreen
import com.example.nani.screens.Projects.ProjectsScreen
import com.example.nani.screens.Signup.SignUpScreen
import com.example.nani.ui.theme.components.bottomIconColor
import com.example.nani.ui.theme.components.bottomIconImageColor
import kotlinx.coroutines.delay

enum class JairosoftAppScreen(@StringRes val title: Int) {
    Login(title = R.string.app_name),
    Forgot(title = R.string.forgot_password),
    Signup(title = R.string.signup),
    Dashboard(title = R.string.lblDashboard),
    Analytics(title = R.string.lblAnalytics),
    Projects(title = R.string.lblprojects),
    Profile(title = R.string.lblprofile)

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JairosoftApp() {
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(context))
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = JairosoftAppScreen.valueOf(
        backStackEntry?.destination?.route ?: JairosoftAppScreen.Login.name
    )
    var isGreen by remember { mutableStateOf(true) }
    val fabColor by animateColorAsState(
        targetValue = if (isGreen) Color.Green else Color.Red,
        animationSpec = tween(durationMillis = 300),
        label = "FAB Color Animation"
    )
    val fabRotation by animateFloatAsState(
        targetValue = if (isGreen) 0f else 180f,
        animationSpec = tween(durationMillis = 300),
        label = "FAB Rotation Animation"
    )
    val loginResult by loginViewModel.loginResult.collectAsState()

    val fabIcon = if (isGreen) painterResource(R.drawable.plus) else painterResource(R.drawable.square)
    val showBottomBarAndFab = loginResult != null && currentScreen in listOf(
        JairosoftAppScreen.Dashboard,
        JairosoftAppScreen.Analytics,
        JairosoftAppScreen.Projects,
        JairosoftAppScreen.Profile
    )

    Scaffold(
        bottomBar = {
            if (showBottomBarAndFab) {
                JairosoftAppBar(navController)
            }
        },
        floatingActionButton = {
            if (showBottomBarAndFab) {

                FloatingActionButton(
                    onClick = {
                        isGreen = !isGreen
                    },
                    shape = CircleShape,
                    containerColor = fabColor,
                    elevation = FloatingActionButtonDefaults.elevation(12.dp),
                    modifier = Modifier
                        .size(80.dp)
                        .offset(y = 50.dp)
                ){
                    Icon(
                        painter = fabIcon,
                        contentDescription = "FAB Icon",
                        tint = Color.White,
                        modifier = Modifier.rotate(fabRotation)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = JairosoftAppScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = JairosoftAppScreen.Login.name) {
                loginResult?.let {
                    LaunchedEffect(loginResult) {
                        navController.navigate(JairosoftAppScreen.Dashboard.name) {
                            popUpTo(JairosoftAppScreen.Login.name) { inclusive = true }
                        }
                    }
                }
                LoginGroup(
                    onForgotPassword = { navController.navigate(JairosoftAppScreen.Forgot.name) },
                    onLogin = { email, password -> loginViewModel.loginUser(email, password) },
                    loginViewModel = loginViewModel
                )
            }

            composable(route = JairosoftAppScreen.Forgot.name) { ForgotPasswordScreen(navController) }
            composable(route = JairosoftAppScreen.Dashboard.name) {
                Log.d("NavDebug", "DashboardScreen Composable loaded")
                DashboardScreen(navController)
            }
            composable(route = JairosoftAppScreen.Signup.name) { SignUpScreen(navController) }
            composable(route = JairosoftAppScreen.Analytics.name) { AnalyticsScreen(navController) }
            composable(route = JairosoftAppScreen.Projects.name) { ProjectsScreen(navController) }
            composable(route = JairosoftAppScreen.Profile.name) { ProfileScreen(navController) }
        }
    }
}



@Composable
fun JairosoftAppBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.height(80.dp),
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
                Spacer(modifier = Modifier.padding(35.dp))
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






