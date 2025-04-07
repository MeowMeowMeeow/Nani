package com.example.nani.ui.theme.components

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nani.data.repository.TimeInRepository
import com.example.nani.data.model.TimeInRequest
import com.example.nani.data.model.TimeOutRequest
import com.example.nani.data.repository.TimeTrackingRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
class TimeTrackingViewModel(
    private val timeInRepository: TimeInRepository = TimeInRepository(),
    private val timeTrackingRepository: TimeTrackingRepository = TimeTrackingRepository()
) : ViewModel() {

    private val _timeTrackingState = MutableLiveData<Boolean>(false)  // False means clocked out, True means clocked in
    val timeTrackingState: LiveData<Boolean> = _timeTrackingState

    private val _snackbarMessage = MutableLiveData<String>()
    val snackbarMessage: LiveData<String> = _snackbarMessage

    private var clockInTime: Long? = null  // Store clock-in time

    // Toggle Time Tracking
    fun toggleTimeTracking(context: Context) {
        val user = SessionManager.getUser(context)
        val userId = user?.user_id ?: return  // Make sure user is logged in
        val token = SessionManager.getToken(context) ?: return

        viewModelScope.launch {
            if (_timeTrackingState.value == false) {
                // Clock In logic
                clockInTime = System.currentTimeMillis() / 1000L
                val date = clockInTime?.let { formatDateClock(it) }

                val timeInRequest = TimeInRequest(
                    timeIn = formatDateClock(clockInTime ?: 0),
                    date = date ?: "",
                    attendanceStatus = "Whole-Day",
                    timeInMins = 0,  // Time-in minutes remains 0 initially
                    totalLateMins = 0,  // You can calculate late minutes here if needed
                    userId = userId
                )

                // Post Time-In Request using TimeInRepository
                try {
                    val response = timeInRepository.postTimeIn(token, timeInRequest)
                    Log.d("TimeTracking", "Time In Response: ${response.body()}")

                    if (response.isSuccessful) {
                        Log.d("TimeTracking", "Clock In Successful")
                        _snackbarMessage.postValue("Clocked In")
                    } else {
                        Log.e("TimeTracking", "Clock In Failed: ${response.message()}")
                        _snackbarMessage.postValue("Clock In Failed: ${response.message()}")
                    }
                } catch (e: Exception) {
                    Log.e("TimeTracking", "Error in clocking in: ${e.localizedMessage}")
                    _snackbarMessage.postValue("Clock In Failed: ${e.localizedMessage}")
                }

                SessionManager.saveClockInTime(context, clockInTime ?: 0L)
                SessionManager.saveTimeTrackingState(context, true)

            } else {

                // Clock Out logic
                val clockOutUnix = System.currentTimeMillis() / 1000L
                val savedClockInTime = SessionManager.getClockInTime(context)
                val durationSeconds = clockOutUnix - savedClockInTime
                val timeOutMinutes = durationSeconds / 60

                val attendanceStatus = if (timeOutMinutes >= 240) "Whole-Day" else "Half-Day"

                val timeOutRequest = TimeOutRequest(
                    userId = userId,
                    timeOut = formatDateClock(clockOutUnix),
                    timeOutMinutes = timeOutMinutes.toLong(),
                    totalHoursWorked = timeOutMinutes / 60,
                    totalUndertime = if (timeOutMinutes < 480) (480 - timeOutMinutes) else 0
                )


                // Post Time-Out
                try {
                    val response = timeTrackingRepository.postTimeOut(token, timeOutRequest)
                    Log.d("TimeTracking", "Time Out Response: ${response.body()}") // Log response

                    if (response.isSuccessful) {
                        Log.d("TimeTracking", "Clock Out Successful: $attendanceStatus")
                        _snackbarMessage.postValue("Clocked Out: $attendanceStatus")
                    } else {
                        Log.e("TimeTracking", "Clock Out Failed: ${response.message()}")
                        _snackbarMessage.postValue("Clock Out Failed: ${response.message()}")
                    }
                } catch (e: Exception) {
                    Log.e("TimeTracking", "Error in clocking out: ${e.localizedMessage}")
                    _snackbarMessage.postValue("Clock Out Failed: ${e.localizedMessage}")
                }


                SessionManager.saveTimeTrackingState(context, false)
                SessionManager.saveClockInTime(context, 0L)
                clockInTime = null
            }


            _timeTrackingState.value = !_timeTrackingState.value!!
        }
    }




}
