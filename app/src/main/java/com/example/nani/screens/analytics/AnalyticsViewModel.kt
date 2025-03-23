package com.example.nani.screens.analytics

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nani.data.AnalyticsRepository

import com.example.nani.data.UserLogs
import com.example.nani.network.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch


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
        this.token = token
    }

    fun fetchLogs(token: String? = this.token) {
        val authToken = token ?: run {
            _errorMessage.value = "Token is missing"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val result = repository.getLogs(authToken)
                _logs.value = result
                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch logs: ${e.localizedMessage}"
                _isLoading.value = false
            }
        }
    }
}

