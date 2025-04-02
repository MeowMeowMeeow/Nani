package com.example.nani.screens.dashboard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.nani.JairosoftAppScreen
import com.example.nani.R
import com.example.nani.data.UserLogs
import com.example.nani.screens.analytics.AnalyticsViewModel
import com.example.nani.screens.analytics.TableCell
import com.example.nani.screens.login.LoginViewModel
import com.example.nani.ui.theme.components.ProgressBar
import com.example.nani.ui.theme.components.TokenStorage
import com.example.nani.ui.theme.components.formatDate
import com.example.nani.ui.theme.components.formatTime
import com.example.nani.ui.theme.components.getUserCity
import com.example.nani.ui.theme.components.requestUpdatedLocation
import com.example.nani.ui.theme.components.tablePadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//update sa viewmodel only after nag login once ra siya mu animate
@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: AnalyticsViewModel,
    loginViewModel: LoginViewModel
) {
    val context = LocalContext.current
    val tokenStorage = remember { TokenStorage(context) }

    val logs by viewModel.logs
    val user = loginViewModel.details.collectAsState().value
    val loginToken = user?.token.orEmpty()

    val currentDate = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).format(Date())
    var cityName by remember { mutableStateOf("Undisclosed Location") }

    val visibleDateCard = remember { mutableStateOf(false) }
    val visibleProjectsCard = remember { mutableStateOf(false) }
    val visibleAttendanceCard = remember { mutableStateOf(false) }
    val visibleTrackedHoursCard = remember { mutableStateOf(false) }

    var hasInitialized by remember { mutableStateOf(false) }

    // Get the initial location when the screen is loaded
    LaunchedEffect(Unit) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(context, "Please enable location services", Toast.LENGTH_SHORT).show()
        } else {
            try {
                cityName = getUserCity(context)
                Log.d("Dashboard", "Initial location: $cityName")
            } catch (e: Exception) {
                cityName = "Error getting location"
                Log.e("Dashboard", "Error getting initial location: ${e.message}")
            }
        }
    }
    DisposableEffect(Unit) {
        // Create the BroadcastReceiver
        val locationReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
                    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val updatedCityName = requestUpdatedLocation(context)
                            cityName = updatedCityName.ifEmpty { "Unknown" }
                            Log.d("Dashboard", "Location re-enabled: $cityName")
                        }
                    } else {
                        cityName = "Discreet location"
                        Log.d("Dashboard", "Location services disabled")
                    }
                }
            }
        }

        // Register the receiver
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        context.registerReceiver(locationReceiver, filter)

        // Unregister the receiver on dispose
        onDispose {
            context.unregisterReceiver(locationReceiver)
        }
    }

    LaunchedEffect(Unit) {
        if (!visibleDateCard.value) {
            delay(200)
            visibleDateCard.value = true
            delay(300)
            visibleProjectsCard.value = true
            delay(450)
            visibleAttendanceCard.value = true
            delay(450)
            visibleTrackedHoursCard.value = true
        }
    }

    LaunchedEffect(Unit) {
        if (!hasInitialized) {
            hasInitialized = true
            val storedToken = tokenStorage.getToken()

            if (!storedToken.isNullOrEmpty()) {
                viewModel.setToken(storedToken)
                viewModel.fetchLogs(storedToken)
            } else if (loginToken.isNotEmpty()) {
                viewModel.setToken(loginToken)
                viewModel.fetchLogs(loginToken)
                tokenStorage.saveToken(loginToken)
            }
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier.padding(bottom = 5.dp)
            ) {
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
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Column {
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Dashboard",
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Date Card with City Name
                AnimatedVisibility(visible = visibleDateCard.value) {
                    DateDashboardCard(
                        icon = R.drawable.calendar,
                        title = "Today is $currentDate",
                        subtitle = "Have a productive day!"
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                AnimatedVisibility(visible = visibleProjectsCard.value) {
                    LocationsCard(
                        icon = R.drawable.map,
                        title = "$cityName Vibes!",
                        subtitle = "How's the weather at $cityName?"
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                AnimatedVisibility(visible = visibleAttendanceCard.value) {
                    AttendanceCard(
                        navController = navController,
                        logs = logs
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                AnimatedVisibility(visible = visibleTrackedHoursCard.value) {
                    TrackedCard()
                }
            }
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
fun LocationsCard(icon: Int, title: String, subtitle: String) {
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
    logs: List<UserLogs>,
    navController: NavController
) {
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
                    painter = painterResource(id = R.drawable.logs),
                    contentDescription = "Calendar Icon",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Attendance",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = "Clock Icon",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start= 8.dp)
                    .background(MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0.3F)),
                horizontalArrangement = Arrangement.SpaceEvenly,

            ) {
                Text(

                    text = "Date",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier .width( 80.dp)
                )


                Spacer(modifier = Modifier.width(tablePadding()))
                Spacer(modifier = Modifier.width(tablePadding()))
                Spacer(modifier = Modifier.width(tablePadding()))
                Spacer(modifier = Modifier.width(tablePadding()))
                Text(
                    text = "Time In",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier .width( 80.dp)
                )
                Spacer(modifier = Modifier.width(tablePadding()))
                Spacer(modifier = Modifier.width(tablePadding()))
                Spacer(modifier = Modifier.width(tablePadding()))
                Spacer(modifier = Modifier.width(tablePadding()))
                Text(
                    text = "Time Out",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier .width( 80.dp)
                )

            }

            val sortedLogs = logs.sortedByDescending { it.date }.take(3)
            LazyColumn (  modifier = Modifier.heightIn(max = 200.dp)){
                items(sortedLogs) { userLogs ->

                    val formattedDate = formatDate(userLogs.date)
                    val formattedTimeIn = formatTime(userLogs.timeIn)
                    val formattedTimeOut = formatTime(userLogs.timeOut)
                    val horizontalScrollState = rememberScrollState()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .horizontalScroll(horizontalScrollState),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TableCell(formattedDate, width = 60.dp)
                        Spacer(modifier = Modifier.width(tablePadding()))
                        Spacer(modifier = Modifier.width(tablePadding()))
                        Spacer(modifier = Modifier.width(tablePadding()))
                        Spacer(modifier = Modifier.width(tablePadding()))
                        TableCell(formattedTimeIn, width = 80.dp)
                        Spacer(modifier = Modifier.width(tablePadding()))
                        Spacer(modifier = Modifier.width(tablePadding()))
                        Spacer(modifier = Modifier.width(tablePadding()))
                        Spacer(modifier = Modifier.width(tablePadding()))
                        TableCell(formattedTimeOut, width = 80.dp)
                    }
                    Spacer(modifier = Modifier.height(1.dp))

                    Log.d("AnalyticsTable", "Date raw: ${userLogs.date}, TimeIn raw: ${userLogs.timeIn}, TimeOut raw: ${userLogs.timeOut}")
                }
            }
            Button(
                onClick = { navController.navigate(JairosoftAppScreen.Analytics.name) },
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
fun TrackedCard() {
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
            Column {

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
                    Column {
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
