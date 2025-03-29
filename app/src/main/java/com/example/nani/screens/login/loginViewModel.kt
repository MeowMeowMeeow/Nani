package com.example.nani.screens.login


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nani.data.AnalyticsRepository
import com.example.nani.data.ErrorResponse
import com.example.nani.data.User
import com.example.nani.data.UserLogs
import com.example.nani.data.UserRepository
import com.example.nani.network.data.RetrofitInstance
import com.example.nani.ui.theme.components.SessionManager
import com.example.nani.ui.theme.components.getUserCity
import com.example.nani.ui.theme.components.hasLocationPermission
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class LoginViewModel(
    private val userRepository: UserRepository = UserRepository(),
    private val analyticsRepository: AnalyticsRepository = AnalyticsRepository()
) : ViewModel() {

    private val _details = MutableStateFlow<User?>(null)
    val details = _details.asStateFlow()

    private val _logs = MutableStateFlow<List<UserLogs>>(emptyList())
    val logs = _logs.asStateFlow()

    private val _cityName = MutableStateFlow("Unknown")
    val cityName: StateFlow<String> = _cityName

    fun updateCityName(context: Context) {
        viewModelScope.launch {
            if (hasLocationPermission(context)) {
                _cityName.value = getUserCity(context)
            } else {
                _cityName.value = "Unknown"
            }
        }
    }
    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        context: Context,
    ) {
        viewModelScope.launch {
            try {
                val user = userRepository.loginUser(email, password)

                if (user.status.equals("success", ignoreCase = true)) {
                    _details.value = user
                    SessionManager.saveUser(context, user)

                    fetchLogs(user.response.token)

                    onSuccess()
                } else {
                    val errorMessage = when (user.status.lowercase()) {
                        "failed" -> "Incorrect Email or Password"
                        else -> "Login failed. Status: ${user.status}"
                    }

                    onFailure(errorMessage)
                }

            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is UnknownHostException -> "No internet connection"
                    is SocketTimeoutException -> "Server timeout. Please try again."
                    is HttpException -> {
                        val errorBody = e.response()?.errorBody()?.string()

                        val parsedError = try {
                            val gson = com.google.gson.Gson()
                            gson.fromJson(errorBody, ErrorResponse::class.java)
                        } catch (jsonException: Exception) {
                            null
                        }

                        parsedError?.message ?: "HTTP error ${e.code()}: ${e.message()}"
                    }
                    else -> "An unexpected error occurred: ${e.localizedMessage ?: e.toString()}"
                }

                onFailure(errorMessage)
            }
        }
    }


    private fun fetchLogs(token: String) {
        viewModelScope.launch {
            try {
                val logsResponse = analyticsRepository.getLogs(token)
                _logs.value = logsResponse
            } catch (e: Exception) {

            }
        }
    }
}
