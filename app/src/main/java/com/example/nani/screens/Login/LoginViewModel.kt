package com.example.nani.screens.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nani.data.UserEntity
import com.example.nani.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LoginViewModel(private val repository: UserRepository) : ViewModel()  {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _loginResult = MutableStateFlow<UserEntity?>(null)
    val loginResult: StateFlow<UserEntity?> = _loginResult.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.login(email, password)
            viewModelScope.launch(Dispatchers.Main) { // Ensure state is updated on Main thread
                if (user != null) {
                    _loginResult.value = user // User exists, allow login
                } else {
                    _errorMessage.value = "Invalid email or password"
                }
            }
        }
    }
}
