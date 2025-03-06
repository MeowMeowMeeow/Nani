package com.example.nani.screens.Dashboard

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.nani.ui.theme.components.ProgressBar
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DashboardScreen(navController: NavHostController) {
    val currentDate = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).format(Date())
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
            DateDashboardCard(
                icon = R.drawable.calendar,
                title = "Today is $currentDate",
                subtitle = "Have a productive day!"
            )
            Spacer(modifier = Modifier.height(30.dp))
            // On-Going Projects Card
            ProjectsCard(
                icon = R.drawable.folder,
                title = "On-Going Projects",
                subtitle = "--               --                --"
            )
            Spacer(modifier = Modifier.height(30.dp))
            // Attendance Card
            AttendanceCard(
                navController = navController
            )
            Spacer(modifier = Modifier.height(30.dp))
            // Tracked Hours Card
            TrackedHoursCard()
        }
        // Bottom Navigation Bar

    }
}
}

@Composable
fun DateDashboardCard(icon: Int, title: String, subtitle: String) {
    Card(
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
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceVariant)
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
    Card(
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
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceVariant)
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
    Card(
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
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceVariant)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Attendance", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = "Clock Icon",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceVariant)
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
                onClick = {JairosoftAppScreen.Analytics},
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
    Card(
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
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceVariant)
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
            navController = rememberNavController()
        )
    }

}
