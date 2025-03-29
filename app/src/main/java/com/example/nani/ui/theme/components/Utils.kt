package com.example.nani.ui.theme.components

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nani.R
import com.example.nani.JairosoftAppScreen
import com.example.nani.data.User
import com.example.nani.data.UserResponse
import com.example.nani.ui.theme.NaNiTheme
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun requestLocationPermission(activity: Activity) {
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        1001
    )
}

@SuppressLint("MissingPermission")
@Composable
fun GetUserCity(onCityRetrieved: (String) -> Unit) {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    LaunchedEffect(Unit) {
        if (hasLocationPermission(context)) {
            try {
                val location = fusedLocationClient.lastLocation.await()
                location?.let {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val city = addresses[0].locality ?: "Unknown City"
                        onCityRetrieved(city)
                    }
                }
            } catch (e: Exception) {
                Log.e("CityError", "Error getting city: ${e.message}")
            }
        } else {
            requestLocationPermission(context as Activity)
            Toast.makeText(context, "Location permission required", Toast.LENGTH_SHORT).show()
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun GetUserLocation() {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    LaunchedEffect(Unit) {
        if (hasLocationPermission(context)) {
            try {
                val location = fusedLocationClient.lastLocation.await()
                location?.let {
                    Log.d("Location", "Latitude: ${it.latitude}, Longitude: ${it.longitude}")
                } ?: Log.d("Location", "Location is null")
            } catch (e: Exception) {
                Log.e("Location", "Error getting location: ${e.message}")
            }
        } else {
            requestLocationPermission(context as Activity)
            Toast.makeText(context, "Location permission required", Toast.LENGTH_SHORT).show()
        }
    }
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
                        "Projects"
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


fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
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



