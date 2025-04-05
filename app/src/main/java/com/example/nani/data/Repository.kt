package com.example.nani.data

import android.util.Log
import com.example.nani.network.data.ProjectDao
import com.example.nani.network.data.RetrofitInstance
import com.example.nani.network.data.RetrofitInstance.api
import kotlinx.coroutines.flow.Flow
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

        val response = api.getLogs("Bearer $token")

        Log.d("AnalyticsRepository", "Raw response status: ${response.status}")
        Log.d("AnalyticsRepository", "Logs count: ${response.response.logs.size}")

        response.response.logs.forEachIndexed { index, log ->
            Log.d("AnalyticsRepository", "Log #$index: $log")
        }

        return response.response.logs
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






