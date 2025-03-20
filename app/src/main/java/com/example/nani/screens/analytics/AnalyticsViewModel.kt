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

    init{
        fetchLogs()
    }

    private fun fetchLogs(){

        viewModelScope.launch{
            try {
                _userLogs.value = RetrofitInstance.api.getLogs()
            } catch (_: Exception){

            }
        }
    }



}
