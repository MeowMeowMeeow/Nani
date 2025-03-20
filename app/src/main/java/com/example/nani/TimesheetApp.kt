package com.example.nani

import android.app.Application
import com.example.nani.network.data.DefaultRepository
import com.example.nani.network.data.UserRetrofit


class TimesheetAppApplication : Application(){
    lateinit var container : UserRetrofit

    override fun onCreate() {
        super.onCreate()
        container = DefaultRepository()
    }
}
