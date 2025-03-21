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

import com.example.nani.data.UserLogs
import com.example.nani.network.data.RetrofitInstance

import kotlinx.coroutines.launch

class AnalyticsViewModel : ViewModel() {

    private val _userLogs = mutableStateOf<List<UserLogs>>(emptyList())
    val logs: State<List<UserLogs>> = _userLogs

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    fun fetchLogs(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val bearerToken = "Bearer $token"
                val logsResponse = RetrofitInstance.api.getLogs(bearerToken)
                _userLogs.value = logsResponse
                _errorMessage.value = ""
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load logs: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}


