package com.example.nani.ui.theme.components


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.pdf.PdfDocument
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Looper
import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nani.JairosoftAppScreen
import com.example.nani.R
import com.example.nani.data.model.User
import com.example.nani.data.model.UserLogs
import com.example.nani.data.model.UserResponse
import com.example.nani.ui.theme.NaNiTheme
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}



@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("MissingPermission")
suspend fun getUserCity(context: Context): String {
    return try {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val location = suspendCancellableCoroutine<Location?> { continuation ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    continuation.resume(location) {}
                }
                .addOnFailureListener { exception ->
                    continuation.resume(null) {
                        Log.e("CityError", "Error: $exception")
                    }
                }
        }

        location?.let {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                addresses[0].locality ?: "Unknown City"
            } else {
                "Unknown City"
            }
        } ?: "Location not available"
    } catch (e: Exception) {
        Log.e("CityError", "Error getting city: ${e.message}")
        "Error getting city"
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("MissingPermission")
suspend fun requestUpdatedLocation(context: Context): String {
    return try {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val location = suspendCancellableCoroutine<Location?> { continuation ->
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 1000L
            ).apply {
                setMinUpdateIntervalMillis(500L)
                setMaxUpdates(1)
            }.build()

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    continuation.resume(locationResult.lastLocation) {}
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }

        location?.let {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                addresses[0].locality ?: "Unknown City"
            } else {
                "Unknown City"
            }
        } ?: "Location not available"
    } catch (e: Exception) {
        Log.e("CityError", "Error requesting updated location: ${e.message}")
        "Error getting city"
    }
}

fun createPdfDocument(logs: List<UserLogs>): PdfDocument {
    val pdfDocument = PdfDocument()

    // Page size (width and height)
    val pageInfo = PdfDocument.PageInfo.Builder(600, 800, 1).create()  // Increased width for table content
    var page = pdfDocument.startPage(pageInfo)

    var canvas = page.canvas
    val paint = android.graphics.Paint().apply {
        color = android.graphics.Color.BLACK
        textSize = 12f
    }

    val columnWidths = listOf(100f, 100f, 100f, 80f)
    var yPos = 50f

    // Header row
    val headerTexts = listOf(
        "Date", "Time In", "Time Out", "Total Hours"
    )

    var xPos = 10f
    headerTexts.forEachIndexed { index, text ->
        canvas.drawText(text, xPos, yPos, paint)
        xPos += columnWidths[index]
    }

    yPos += 30

    // Draw data rows
    val addNewPageIfNeeded = {
        if (yPos > pageInfo.pageHeight - 50) {
            pdfDocument.finishPage(page)
            page = pdfDocument.startPage(pageInfo)  // Start a new page
            canvas = page.canvas
            yPos = 50f
        }
    }

    if (logs.isEmpty()) {

        xPos = 10f
        listOf("   -", "     -", "      -", "          -").forEachIndexed { index, text ->
            canvas.drawText(text, xPos, yPos, paint)
            xPos += columnWidths[index]
        }
        yPos += 30
    } else {
        logs.forEach { log ->
            val formattedDate = formatDate(log.date)
            val formattedTimeIn = formatTime(log.timeIn)
            val formattedTimeOut = formatTime(log.timeOut)
            val totalHours = "${log.totalHours ?: 0} hrs"

            xPos = 10f
            val rowData = listOf(
                formattedDate, formattedTimeIn, formattedTimeOut, totalHours
            )

            rowData.forEachIndexed { index, text ->
                canvas.drawText(text, xPos, yPos, paint)
                xPos += columnWidths[index]
            }

            yPos += 30
            addNewPageIfNeeded()
        }
    }

    pdfDocument.finishPage(page)
    return pdfDocument
}



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
                    Spacer(modifier = Modifier.padding(start= middlePadding()))
                    BottomNavItem(
                        navController,
                        JairosoftAppScreen.Projects,
                        R.drawable.projects,
                        "Projects",
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
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
            .padding(start = padding() , end = padding())
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
fun bottomIconImageColor(navController: NavController, label: JairosoftAppScreen): ColorFilter {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    return if (currentRoute == label.name) ColorFilter.tint(MaterialTheme.colorScheme.secondary)
    else ColorFilter.tint(MaterialTheme.colorScheme.primary)

}

fun formatDate(unixTime: Long?): String {
    return if (unixTime != null && unixTime != 0L) {
        try {
            val millis = if (unixTime < 1000000000000L) unixTime * 1000 else unixTime
            val date = java.util.Date(millis)
            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            outputFormat.format(date)
        } catch (e: Exception) {
            "-"
        }
    } else {
        "-"
    }
}

fun formatTime(unixTime: Long?): String {
    return if (unixTime != null && unixTime != 0L) {
        try {
            val millis = if (unixTime < 1000000000000L) unixTime * 1000 else unixTime
            val date = java.util.Date(millis)
            val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            outputFormat.format(date)
        } catch (e: Exception) {
            "-"
        }
    } else {
        "-"
    }
}


object SessionManager {

    private const val PREF_NAME = "nani_app_prefs"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_TOKEN = "token"
    private const val KEY_CLOCK_IN_TIME = "clock_in_time"
    private const val KEY_TIME_TRACKING_STATE = "time_tracking_state"

    private var currentUser: UserResponse? = null
    private var token: String? = null

    private fun getPrefs(context: Context) =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveUser(context: Context, user: User) {
        currentUser = user.response
        token = user.token

        getPrefs(context).edit().apply {
            putString(KEY_USER_ID, user.response.user_id)
            putString(KEY_TOKEN, user.token)
            apply()
        }
    }

    fun getUser(context: Context): UserResponse? {
        if (currentUser == null) {
            val userId = getPrefs(context).getString(KEY_USER_ID, null)
            val savedToken = getPrefs(context).getString(KEY_TOKEN, null)

            if (userId != null && savedToken != null) {
                currentUser = UserResponse(
                    token = savedToken,
                    user_id = userId,
                    expires = 0
                )
                token = savedToken
            }
        }
        return currentUser
    }

    fun getToken(context: Context): String? {
        if (token == null) {
            token = getPrefs(context).getString(KEY_TOKEN, null)
        }
        return token
    }

    // Save clock-in time
    fun saveClockInTime(context: Context, clockInTime: Long) {
        getPrefs(context).edit().apply {
            putLong(KEY_CLOCK_IN_TIME, clockInTime)
            apply()
        }
    }

    // Retrieve clock-in time
    fun getClockInTime(context: Context): Long {
        return getPrefs(context).getLong(KEY_CLOCK_IN_TIME, 0L)
    }

    // Save time tracking state (clocked in or clocked out)
    fun saveTimeTrackingState(context: Context, isClockedIn: Boolean) {
        getPrefs(context).edit().apply {
            putBoolean(KEY_TIME_TRACKING_STATE, isClockedIn)
            apply()
        }
    }

    // Retrieve time tracking state
    fun getTimeTrackingState(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_TIME_TRACKING_STATE, false)
    }

    // Clear user info and clock-in time
    fun clearUser(context: Context) {
        currentUser = null
        token = null

        getPrefs(context).edit().clear().apply()
    }
}

class TokenStorage(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }

    fun clearToken() {
        prefs.edit().remove("auth_token").apply()
    }
}

// Function to format date as MM/dd/yyyy hh:mm a
fun timeDate(date: Date): String {
    val format = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault())
    return format.format(date)
}

// Function to format time as hh:mm a
fun timeTime(date: Date): String {
    val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return format.format(date)
}


fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

fun formatDateClock(unixTime: Long): String {
    val sdf = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault())
    val formattedDate = sdf.format(Date(unixTime * 1000))
    return formattedDate.lowercase(Locale.getDefault())
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES,showBackground = true)
fun PreviewJairoBar() {
    NaNiTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ){
        JairosoftAppBar(
            navController = rememberNavController()
            )
        }
    }
}



