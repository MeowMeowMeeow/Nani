package com.example.nani.network

import com.example.nani.data.LogsResponse
import com.example.nani.data.TimeInRequest
import com.example.nani.data.TimeInResponse
import com.example.nani.data.TimeOutRequest
import com.example.nani.data.User
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String): User

    @GET("logs")
    suspend fun getLogs(
        @Header("Authorization") token: String
    ): LogsResponse

    @POST("time-in")
    suspend fun postTimeIn(
        @Header("Authorization") token: String,
        @Body timeInRequest: TimeInRequest
    ): Response<Unit> // Time-in API (no specific response)

    @POST("time-out")
    suspend fun postTimeOut(
        @Header("Authorization") token: String,
        @Body timeOutRequest: TimeOutRequest
    ): Response<Unit>
}