package com.example.nani.repository

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nani.data.UserDao
import com.example.nani.data.UserDatabase
import com.example.nani.data.UserEntity
import com.example.nani.screens.login.LoginViewModel
import com.example.nani.screens.signup.SignUpViewModel

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun getUserById(userId: String): UserEntity? {
        return userDao.getUserById(userId)
    }  //to be applied

    suspend fun login(email: String, password: String): UserEntity? {
        return userDao.login(email, password)
    }


}

class SignUpViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            val userDao = UserDatabase.getDatabase(context).userDao()
            val repository = UserRepository(userDao)
            return SignUpViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val userDao = UserDatabase.getDatabase(context).userDao() // Get UserDao from DB
            val repository = UserRepository(userDao) // Pass UserDao to Repository
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

