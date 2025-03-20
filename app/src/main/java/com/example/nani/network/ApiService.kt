package com.example.nani.network

import com.example.nani.data.UserLogs

import retrofit2.http.GET

interface ApiService {
    @GET("logs")
    suspend fun getLogs(): List<UserLogs>
}