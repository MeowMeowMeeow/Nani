package com.example.nani.screens.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nani.data.AnalyticsRepository
import com.example.nani.data.User
import com.example.nani.data.UserLogs
import com.example.nani.data.UserRepository
import com.example.nani.network.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository = UserRepository(),
    private val analyticsRepository: AnalyticsRepository = AnalyticsRepository()
) : ViewModel() {

    private val _details = MutableStateFlow<User?>(null)
    val details = _details.asStateFlow()

    private val _logs = MutableStateFlow<List<UserLogs>>(emptyList())
    val logs = _logs.asStateFlow()

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val user = userRepository.loginUser(email, password)

                if (user.status == "success") {
                    _details.value = user

                    fetchLogs(user.response.token)
                    onSuccess()
                } else {
                    onFailure("Login failed: ${user.status}")
                }

            } catch (e: Exception) {
                onFailure("Incorrect Password or Email")
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
