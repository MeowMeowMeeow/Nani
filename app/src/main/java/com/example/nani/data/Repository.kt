package com.example.nani.data

import com.example.nani.network.data.RetrofitInstance

class UserRepository {

    private val api = RetrofitInstance.api

    // Login function
    suspend fun loginUser(email: String, password: String): User {
        return api.loginUser(email, password)
    }

    // Fetch logs function
    suspend fun getLogs(token: String): List<UserLogs> {
        return api.getLogs("Bearer $token")
    }
}


class AnalyticsRepository {

    suspend fun getLogs(token: String): List<UserLogs> {
        return RetrofitInstance.api.getLogs("Bearer $token")
    }


}