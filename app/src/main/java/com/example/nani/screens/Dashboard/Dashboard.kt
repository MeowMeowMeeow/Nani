package com.example.nani.screens.Dashboard

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nani.R
import com.example.nani.ui.theme.NaNiTheme
import com.example.nani.ui.theme.components.JairosoftAppBar
import com.example.nani.ui.theme.components.ProgressBar
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

//update sa viewmodel only after nag login once ra siya mu animate
@Composable
fun DashboardScreen(navController: NavHostController) {
    val currentDate = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).format(Date())
    val visibleDateCard = remember { mutableStateOf(false) }
    val visibleProjectsCard = remember { mutableStateOf(false) }
    val visibleAttendanceCard = remember { mutableStateOf(false) }
    val visibleTrackedHoursCard = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!visibleDateCard.value) {
            delay(300)
            visibleDateCard.value = true
            delay(350)
            visibleProjectsCard.value = true
            delay(400)
            visibleAttendanceCard.value = true
            delay(450)
            visibleTrackedHoursCard.value = true
        }
    }
    Surface (
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
    Column (
        modifier = Modifier
        .padding(top = 10.dp, start = 10.dp, end = 10.dp)
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        Box(
            modifier = Modifier.padding(bottom = 5.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.jairosoft),
                contentDescription = "Logo",
                modifier = Modifier.size(40.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Jairosoft",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge,
                    )
            }
        }
        Spacer(
            modifier = Modifier.height(14.dp)
        )
        Column(
            modifier = Modifier
        ) {
            // Left-aligned Company Logo and Name
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Dashboard",
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Today's Date Card
            AnimatedVisibility(
                visible = visibleDateCard.value,
                enter = slideInVertically(
                    initialOffsetY = { -100 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 600))
            ) {
            DateDashboardCard(
                icon = R.drawable.calendar,
                title = "Today is $currentDate",
                subtitle = "Have a productive day!"
            )}
            Spacer(modifier = Modifier.height(30.dp))
            // On-Going Projects Card
            AnimatedVisibility(
                visible = visibleProjectsCard.value,
                enter = slideInVertically(
                    initialOffsetY = { -100 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 600))
            ) {
            ProjectsCard(
                icon = R.drawable.folder,
                title = "On-Going Projects",
                subtitle = "--               --                --"
            )}
            Spacer(modifier = Modifier.height(30.dp))
            // Attendance Card
            AnimatedVisibility(
                visible = visibleAttendanceCard.value,
                enter = slideInVertically(
                    initialOffsetY = { -100 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 600))
            ) {
            AttendanceCard(
                navController = navController
            )}
            Spacer(modifier = Modifier.height(30.dp))
            // Tracked Hours Card
            AnimatedVisibility(
                visible = visibleTrackedHoursCard.value,
                enter = slideInVertically(
                    initialOffsetY = { -100 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 600))
            ) {
            TrackedHoursCard()
        }}

    }
}
}

@Composable
fun DateDashboardCard(icon: Int, title: String, subtitle: String) {
    ElevatedCard(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Icon",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = title,  style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = subtitle,style = MaterialTheme.typography.bodyMedium, color =  MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}

@Composable
fun ProjectsCard(icon: Int, title: String, subtitle: String) {
    ElevatedCard(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Icon",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = subtitle, fontSize = 12.sp, color =  MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}


@Composable
fun AttendanceCard(
    navController: NavController
) {
    ElevatedCard (
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = "Calendar Icon",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Attendance", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = "Clock Icon",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0.3F))
                , horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Date", style =  MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
                Text(text = "Time In", style =  MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
                Text(text = "Time Out", style =  MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = { navController.navigate(JairosoftAppScreen.Analytics.name)},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = ("Show attendance"),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

@Composable
fun TrackedHoursCard() {
    ElevatedCard(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )


    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = "Clock Icon",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tracked Hours",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column() {

                Text(
                    text = "0            2            4            6            8            10",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0.3f))
                        .fillMaxWidth()
                        .padding(start = 30.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row {
                    Column(
                    ) {
                        Text(
                            text = "M",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "T",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "W",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Th",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "F",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.width(15.dp))
                    Column {
                        ProgressBar(
                            modifier = Modifier,
                            progress = 0.8f
                        )
                        Spacer(modifier = Modifier.height(17.dp))
                        ProgressBar(
                            modifier = Modifier,
                            progress = 0.65f
                        )
                        Spacer(modifier = Modifier.height(17.dp))
                        ProgressBar(
                            modifier = Modifier,
                            progress = 0.75f
                        )
                        Spacer(modifier = Modifier.height(17.dp))
                        ProgressBar(
                            modifier = Modifier,
                            progress = 0.72f
                        )
                        Spacer(modifier = Modifier.height(17.dp))
                        ProgressBar(
                            modifier = Modifier,
                            progress = 0.78f
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewDash(){
    NaNiTheme {
        DashboardScreen(
            navController = rememberNavController(),

        )

    }

}
