package com.example.nani.screens.Signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nani.data.UserEntity
import com.example.nani.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _verifyPassword = MutableStateFlow("")
    val verifyPassword: StateFlow<String> = _verifyPassword.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateVerifyPassword(newVerifyPassword: String) {
        _verifyPassword.value = newVerifyPassword
    }

    fun registerUser() {
        val userEmail = email.value.trim()
        val userPass = password.value.trim()
        val userVPass = verifyPassword.value.trim()

        if (userEmail.isEmpty() || userPass.isEmpty() || userVPass.isEmpty()) {
            Log.e("SignUp", "Fields cannot be empty")
            return
        }

        if (userPass != userVPass) {
            Log.e("SignUp", "Passwords do not match")
            return
        }

        val newUser = UserEntity(id = 0, userId = UUID.randomUUID().toString(), email = userEmail, password = userPass)


        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUser(newUser)
            Log.d("SignUp", "User Registered: $userEmail")
        }
    }
}
