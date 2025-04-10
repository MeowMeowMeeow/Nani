package com.example.nani.data.repository

import android.util.Log
import com.example.nani.data.model.TimeInRequest
import com.example.nani.data.model.TimeOutRequest
import com.example.nani.data.model.User
import com.example.nani.data.model.UserLogs
import com.example.nani.data.network.RetrofitInstance
import com.example.nani.data.network.RetrofitInstance.api
import retrofit2.Response

class UserRepository {

    private val api = RetrofitInstance.api

    suspend fun loginUser(email: String, password: String): User {
        Log.d("UserRepository", "Logging in user: $email")
        return api.loginUser(email, password).also {
            Log.d("UserRepository", "Login response: $it")
        }
    }
}



class AnalyticsRepository {

    suspend fun getLogs(token: String): List<UserLogs> {
        Log.d("AnalyticsRepository", "Fetching logs with token: $token")

        val response = RetrofitInstance.api.getLogs("Bearer $token")

        Log.d("AnalyticsRepository", "Logs count: ${response.size}")

        response.forEachIndexed { index, wrapper ->
            Log.d("AnalyticsRepository", "Log #$index: ${wrapper.data}")
        }

        return response.map { it.data }
    }
}


class TimeTrackingRepository {

    private val api = RetrofitInstance.api

    suspend fun postTimeOut(
        token: String,
        timeOutRequest: TimeOutRequest
    ): Response<Unit> {
        return api.postTimeOut("Bearer $token", timeOutRequest)
    }
}

class TimeInRepository {

    private val api = RetrofitInstance.api

    suspend fun postTimeIn(
        token: String,
        timeInRequest: TimeInRequest
    ): Response<Unit> {
        return api.postTimeIn("Bearer $token", timeInRequest)
    }
}






