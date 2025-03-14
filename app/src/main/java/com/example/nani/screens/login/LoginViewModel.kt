package com.example.nani.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nani.data.UserEntity
import com.example.nani.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
    fun setLoginResult(user: UserEntity?) {
        _loginResult.value = user
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.login(email, password)
            withContext(Dispatchers.Main) {  // Ensure state updates happen on the main thread
                _loginResult.value = user
                _errorMessage.value = if (user == null) "Invalid email or password" else null
            }
        }
    }



}
