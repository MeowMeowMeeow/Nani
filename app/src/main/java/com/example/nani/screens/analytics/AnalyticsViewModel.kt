package com.example.nani.screens.analytics

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nani.data.repository.AnalyticsRepository
import com.example.nani.data.model.UserLogs
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException


class AnalyticsViewModel(
    private val repository: AnalyticsRepository = AnalyticsRepository()
) : ViewModel() {

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

        viewModelScope.launch {
            try {
                val result = repository.getLogs(authToken)
                Log.d("AnalyticsViewModel", "Logs fetched successfully: ${result.size} entries")

                _logs.value = result

                if (result.isEmpty()) {
                    _errorMessage.value = "No logs found."
                    Log.w("AnalyticsViewModel", "No logs returned from repository")
                }
            } catch (e: SocketTimeoutException) {
                _errorMessage.value = "Failed to fetch logs"
                Log.e("AnalyticsViewModel", "Error fetching logs", e)
            } finally {
                _isLoading.value = false
                Log.d("AnalyticsViewModel", "Loading finished")
            }
        }
    }
}

