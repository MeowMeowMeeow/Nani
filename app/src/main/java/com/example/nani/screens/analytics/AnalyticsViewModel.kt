package com.example.nani.screens.analytics

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nani.data.repository.AnalyticsRepository
import com.example.nani.data.model.UserLogs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException


class AnalyticsViewModel : ViewModel() {

    private val _logs = mutableStateOf<List<UserLogs>>(emptyList())
    val logs: State<List<UserLogs>> = _logs

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private var token: String? = null

    fun setToken(token: String) {
        Log.d("AnalyticsViewModel", "Token set: $token")
        this.token = token
    }

    fun fetchLogs(token: String? = this.token) {
        val authToken = token ?: run {
            _errorMessage.value = "Token is missing"
            Log.e("AnalyticsViewModel", "Token is missing")
            return
        }

        Log.d("AnalyticsViewModel", "Fetching logs with token: $authToken")


        _isLoading.value = true
        _errorMessage.value = null

        // Hardcoded logs
        val logsList = listOf(
            UserLogs(
                date = "Apr 10, 2025 09:00 AM",
                timeIn = "09:00 AM",
                timeOut = "06:00 PM",
                userId = "user_001",
                totalLate = "0 minutes",
                totalUndertime = "0 minutes",
                status = "Present"
            ),
            UserLogs(
                date = "Apr 9, 2025 09:00 AM",
                timeIn = "09:00 AM",
                timeOut = "06:00 PM",
                userId = "user_002",
                totalLate = "15 minutes",
                totalUndertime = "0 minutes",
                status = "Present"
            ),
            UserLogs(
                date = "Apr 8, 2025 09:00 AM",
                timeIn = "09:00 AM",
                timeOut = "06:00 PM",
                userId = "user_003",
                totalLate = "30 minutes",
                totalUndertime = "0 minutes",
                status = "Present"
            )
        )


        viewModelScope.launch {
            try {

                delay(2000)
                Log.d("AnalyticsViewModel", "Logs fetched successfully: ${logsList.size} entries")

                _logs.value = logsList

                if (logsList.isEmpty()) {
                    _errorMessage.value = "No logs found."
                    Log.w("AnalyticsViewModel", "No logs returned")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch logs"
                Log.e("AnalyticsViewModel", "Error fetching logs", e)
            } finally {
                _isLoading.value = false
                Log.d("AnalyticsViewModel", "Loading finished")
            }
        }
    }
}

