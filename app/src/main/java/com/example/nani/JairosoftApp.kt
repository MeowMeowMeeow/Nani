package com.example.nani


import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nani.data.UserResponse

import com.example.nani.screens.analytics.AnalyticsScreen
import com.example.nani.screens.analytics.AnalyticsViewModel

import com.example.nani.screens.dashboard.DashboardScreen
import com.example.nani.screens.login.LoginScreen
import com.example.nani.screens.login.LoginViewModel
import com.example.nani.screens.popUps.ForgotPasswordScreen
import com.example.nani.screens.popUps.SplashScreen
import com.example.nani.screens.profile.ProfileScreen
import com.example.nani.screens.projects.ProjectViewModel
import com.example.nani.screens.projects.ProjectsScreen
import com.example.nani.ui.theme.components.JairosoftAppBar
import com.example.nani.ui.theme.components.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class JairosoftAppScreen(@StringRes val title: Int) {
    Login(title = R.string.app_name),
    Forgot(title = R.string.forgot_password),
    Signup(title = R.string.signup),
    Dashboard(title = R.string.lblDashboard),
    Analytics(title = R.string.lblAnalytics),
    Projects(title = R.string.lblprojects),
    Profile(title = R.string.lblprofile),
    SplashScreen (title = R.string.splashscreen)

}

@Composable
fun JairosoftApp() {
    val context = LocalContext.current
    var currentUser by remember { mutableStateOf<UserResponse?>(null) }
    var token by remember { mutableStateOf<String?>(null) }
    val analyticsViewModel: AnalyticsViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val projectViewModel:ProjectViewModel= viewModel()
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = JairosoftAppScreen.valueOf(
        backStackEntry?.destination?.route ?: JairosoftAppScreen.Login.name
    )
    var isGreen by remember { mutableStateOf(true) }
    val fabColor by animateColorAsState(
        targetValue = if (isGreen) MaterialTheme.colorScheme.tertiaryContainer else Color.Red,
        animationSpec = tween(durationMillis = 300),
        label = "FAB Color Animation"
    )
    val fabRotation by animateFloatAsState(
        targetValue = if (isGreen) 0f else 180f,
        animationSpec = tween(durationMillis = 300),
        label = "FAB Rotation Animation"
    )

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarJob by remember { mutableStateOf<Job?>(null) }
    val fabIcon =
        if (isGreen) painterResource(R.drawable.plus) else painterResource(R.drawable.square)
    var shouldShowBottomBar by remember { mutableStateOf(false) }

    LaunchedEffect(currentScreen) {
        if (currentScreen in listOf(
                JairosoftAppScreen.Dashboard,
                JairosoftAppScreen.Analytics,
                JairosoftAppScreen.Projects,
                JairosoftAppScreen.Profile
            )
        ) {
            delay(200)
            shouldShowBottomBar = true
        } else {
            shouldShowBottomBar = false
        }
    }
    LaunchedEffect(Unit) {
        currentUser = SessionManager.getUser(context)
        token = SessionManager.getToken(context)
    }
    val startDestination =
        if (!token.isNullOrEmpty() && currentUser != null) {
            JairosoftAppScreen.Dashboard.name
        } else {
            JairosoftAppScreen.Login.name
        }


//edit na max width;
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 110.dp)

            )
        },
        bottomBar = {
            if (shouldShowBottomBar) {
                JairosoftAppBar(navController)
            }
        },
        floatingActionButton = {
            if (shouldShowBottomBar) {
                FloatingActionButton(
                    onClick = {
                        isGreen = !isGreen

                        snackbarJob?.cancel()
                        snackbarJob = scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()

                            snackbarHostState.showSnackbar(
                                message = if (isGreen) "Clocked Out" else "Clocked In",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    shape = CircleShape,
                    containerColor = fabColor,
                    elevation = FloatingActionButtonDefaults.elevation(12.dp),
                    modifier = Modifier
                        .size(90.dp)
                        .offset(y = 60.dp, x = 8.dp)
                ) {
                    Icon(
                        painter = fabIcon,
                        contentDescription = "Fab Icon",
                        tint = Color.White,
                        modifier = Modifier.rotate(fabRotation)
                    )
                }
            }
        }, floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = JairosoftAppScreen.SplashScreen.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = JairosoftAppScreen.SplashScreen.name) {
                SplashScreen(navController)
            }
            composable(route = JairosoftAppScreen.Login.name) {
                LoginScreen(
                    navController,
                    viewModel = loginViewModel
                )

            }
            composable(route = JairosoftAppScreen.Forgot.name) {
                ForgotPasswordScreen(navController)
            }
            composable(route = JairosoftAppScreen.Dashboard.name) {
                DashboardScreen(
                    navController = navController,
                    viewModel = analyticsViewModel,
                    loginViewModel = loginViewModel)
            }
            composable(route = JairosoftAppScreen.Analytics.name) {


                AnalyticsScreen(
                    navController = navController,
                    viewModel = analyticsViewModel,
                    loginViewModel = loginViewModel
                )
            }

            composable(route = JairosoftAppScreen.Projects.name) { ProjectsScreen(
                navController,
                viewModel = projectViewModel
            ) }
            composable(route = JairosoftAppScreen.Profile.name) { ProfileScreen(navController) }
        }
    }
}







