package com.example.nani.ui.theme.components

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nani.data.TimeInRepository
import com.example.nani.data.TimeInRequest
import com.example.nani.data.TimeOutRequest
import com.example.nani.data.TimeTrackingRepository
import com.example.nani.data.User
import com.example.nani.network.data.RetrofitInstance
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
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

    // Toggle Time Tracking (Clock In / Clock Out)
    fun toggleTimeTracking(context: Context) {
        val user = SessionManager.getUser(context)  // Get the current user from SessionManager
        val userId = user?.user_id ?: return  // Make sure user is logged in
        val token = SessionManager.getToken(context) ?: return  // Ensure token is available

        viewModelScope.launch {
            if (_timeTrackingState.value == false) {
                // Clock In logic (if clocked out)
                clockInTime = System.currentTimeMillis() / 1000L  // Record the clock-in time in seconds
                val date = clockInTime?.let { formatDate(it) }

                val timeInRequest = TimeInRequest(
                    timeIn = formatDate(clockInTime ?: 0),  // Format clock-in time for POST request
                    date = date ?: "",
                    attendanceStatus = "Whole-Day",  // You can set this to "Working" or any initial status
                    timeInMins = 0,  // Time-in minutes remains 0 initially
                    totalLateMins = 0,  // You can calculate late minutes here if needed
                    userId = userId
                )

                // Post Time-In Request using TimeInRepository
                try {
                    val response = timeInRepository.postTimeIn(token, timeInRequest)
                    Log.d("TimeTracking", "Time In Response: ${response.body()}") // Log response

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

                // Store clock-in time in SessionManager
                SessionManager.saveClockInTime(context, clockInTime ?: 0L)
                SessionManager.saveTimeTrackingState(context, true)

            } else {
                // Clock Out logic (if clocked in)
                val clockOutUnix = System.currentTimeMillis() / 1000L  // Current time in Unix format (seconds)
                val durationSeconds = clockOutUnix - (clockInTime ?: 0L)  // Duration worked in seconds
                val timeOutMinutes = durationSeconds / 60  // Convert seconds to minutes

                val attendanceStatus = if (timeOutMinutes >= 240) "Whole-Day" else "Half-Day"

                val timeOutRequest = TimeOutRequest(
                    userId = userId,
                    timeOut = formatDate(clockOutUnix),  // Format time for POST request
                    timeOutMinutes = timeOutMinutes.toLong(),  // Total worked time in minutes
                    totalHoursWorked = timeOutMinutes / 60,  // Convert minutes to hours
                    totalUndertime = if (timeOutMinutes < 480) (480 - timeOutMinutes) else 0  // Undertime calculation
                )

                // Post Time-Out Request using TimeTrackingRepository
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

                // Clear clock-in time and state from SessionManager
                SessionManager.saveTimeTrackingState(context, false)
                SessionManager.saveClockInTime(context, 0L)
                clockInTime = null
            }

            // Toggle the button color and show snackbar message
            _timeTrackingState.value = !_timeTrackingState.value!!
        }
    }


    // Convert Unix timestamp to a formatted time like "03/17/2025 02:50 pm"
    private fun formatDate(unixTime: Long): String {
        val sdf = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault())
        val formattedDate = sdf.format(Date(unixTime * 1000))
        return formattedDate.lowercase(Locale.getDefault()) // Convert AM/PM to lowercase
    }
}
