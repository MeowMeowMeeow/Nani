package com.example.nani.screens.profile

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nani.R
import com.example.nani.JairosoftAppScreen
import com.example.nani.data.Project
import com.example.nani.data.UserLogs
import com.example.nani.screens.analytics.AnalyticsViewModel
import com.example.nani.screens.login.LoginViewModel
import com.example.nani.screens.projects.ProjectViewModel
import com.example.nani.ui.theme.NaNiTheme
import com.example.nani.ui.theme.components.SessionManager
import com.example.nani.ui.theme.components.arcOffset
import com.example.nani.ui.theme.components.betweenSpace
import com.example.nani.ui.theme.components.cardPadding
import com.example.nani.ui.theme.components.imageOffset
import com.example.nani.ui.theme.components.imageSize
import com.example.nani.ui.theme.components.offset
import com.example.nani.ui.theme.components.sizeCircular
import com.example.nani.ui.theme.components.textSize

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: AnalyticsViewModel,
    loginViewModel: LoginViewModel,
    projectsViewModel: ProjectViewModel // Add ProjectViewModel as a parameter
) {
    val context = LocalContext.current
    var showLogoutDialog by remember { mutableStateOf(false) }

    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    val logs by viewModel.logs
    val user = loginViewModel.details.collectAsState().value
    val token = user?.token ?: ""

    // Fetch all projects
    val projects by projectsViewModel.projects.collectAsState(emptyList())

    LaunchedEffect(token) {
        if (token.isNotEmpty()) {
            viewModel.setToken(token)
            viewModel.fetchLogs(token)
        }
        // Always fetch projects when the profile screen is opened
        projectsViewModel.getAllProjects()
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ProfileGroup(
            onLogoutClick = { showLogoutDialog = true },
            logs = logs,
            projects = projects // Pass the list of projects to the ProfileGroup
        )
    }

    if (showLogoutDialog) {
        LogoutConfirmationDialog(
            onConfirm = {
                SessionManager.clearUser(context)
                navController.navigate(JairosoftAppScreen.Login.name) {
                    popUpTo(JairosoftAppScreen.Dashboard.name) { inclusive = true }
                }
            },
            onDismiss = { showLogoutDialog = false }
        )
    }
}


@Composable
fun ProfileGroup(onLogoutClick: () -> Unit, logs: List<UserLogs>,
                 projects: List<Project>, ) {
    val inProgressCount = projects.count { it.status == "In Progress" }
    val toDoCount = projects.count { it.status == "To Do" }
    val completedCount = projects.count{it.status == "Completed"}
    val total =inProgressCount + toDoCount
    val completed = if (total > 0) (completedCount.toFloat() / total) * 100 else 0f
    val toDoPercent = if (total > 0) (toDoCount.toFloat() / total) * 100 else 0f
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp, top = 20.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onLogoutClick() }
                    ) {
                        Text(
                            text = "Log out",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Image(
                            painter = painterResource(R.drawable.logout),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .padding(5.dp)
                                .size(24.dp)
                        )
                    }
                }

                Image(
                    painter = painterResource(R.drawable.arc),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = arcOffset())
                        .zIndex(0f),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
                .offset(y = -(imageOffset())),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.face),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(imageSize())
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.onSurfaceVariant, CircleShape)
                    .zIndex(2f)
            )
        }
    }

    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.padding(130.dp))
            Text(
                text = "Name Name",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
        Row(  modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,  modifier = Modifier.padding(15.dp)) {
                Text(
                    text = "$inProgressCount",
                    fontSize = 50.sp,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "In Progress",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Divider(
                modifier = Modifier
                    .height(100.dp)
                    .width(1.dp)
                    .background(MaterialTheme.colorScheme.onSurface)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(15.dp)) {
                Text(
                    text = "$toDoCount",
                    fontSize = 50.sp,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "To Do Lists",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )

            }

            Divider(
                modifier = Modifier
                    .height(100.dp)
                    .width(1.dp)
                    .background(MaterialTheme.colorScheme.onSurface)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(15.dp)) {
                Text(
                    text = "$completedCount",
                    fontSize = 50.sp,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Completed",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )

            }

            }
        Spacer(modifier = Modifier.height(30.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Row (modifier = Modifier.fillMaxWidth()){
                Text(
                    text = "Completed",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${completed.toInt()}%",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)

                )
            }
            LinearProgressIndicator(
                progress = completed / 100f,
                modifier = Modifier
                    .padding( start=30.dp, end = 30.dp)
                    .fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer //progress color
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Row (modifier = Modifier.fillMaxWidth()){
                Text(
                    text = "To Do",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${toDoPercent.toInt()}%",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)

                )
            }
            LinearProgressIndicator(
                progress = toDoPercent / 100f,
                modifier = Modifier
                    .padding( start=30.dp, end = 30.dp)
                    .fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer )
            Spacer(modifier = Modifier.height(100.dp))

        }
    }
}

@Composable
fun LogoutConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Text(
                text = "Confirm",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onConfirm() }
            )
        },
        dismissButton = {
            Text(
                text = "Cancel",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onDismiss() } .padding(end = 10.dp)
            )
        },
        title = { Text("Log out") },
        text = { Text("Are you sure you want to log out?", color = MaterialTheme.colorScheme.primary,style= MaterialTheme.typography.labelSmall ) }
    )
}
