package com.example.nani.data.model


import com.google.gson.annotations.SerializedName


//change for the API
//@Entity(tableName = "users")
//data class UserEntity(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    @ColumnInfo(name = "userId") val userId: String,
//    @ColumnInfo(name = "email") val email: String,
//    @ColumnInfo(name = "password") val password: String
//)



data class LogsWrapper(
    @SerializedName("data")
    val data: UserLogs
)

data class UserLogs(
    val date: String,
    val timeIn: String,
    val timeOut: String,
    val userId: String,
    val totalLate: String,
    val totalUndertime: String,
    val status: String
)


data class LogsResponse(
    val status: String,
    val response: LogsList
)

data class LogsList(
    @SerializedName("Logs")
    val logs: List<UserLogs>
)

data class UserResponse(
    val token: String,
    val user_id: String,
    val expires: Long
)

data class User(
    val status: String,
    val response: UserResponse
) {
    val token: String
        get() = response.token
}
data class ErrorResponse(
    val statusCode: Int?,
    val reason: String?,
    val message: String?
)


// TimeInRequest for sending time-in data to the backend
data class TimeInRequest(
    @SerializedName("time-in") val timeIn: String, // Time-In in String format
    @SerializedName("date") val date: String, // Date for clocking in in String format
    @SerializedName("attendance_status") val attendanceStatus: String, // "Whole-Day" or "Half-Day"
    @SerializedName("time-in_mins") val timeInMins: Int, // Time-In duration in minutes
    @SerializedName("total_late_mins") val totalLateMins: Int, // Total late minutes
    @SerializedName("user_id") val userId: String // User ID
)


// TimeInResponse for handling the response after posting time-in
data class TimeInResponse(
    val status: String,
    val message: String
)

// TimeOutRequest for sending time-out data to the backend
data class TimeOutRequest(
    @SerializedName("user_id") val userId: String, // User ID for time-out
    @SerializedName("timeout") val timeOut: String, // Time-out in String format
    @SerializedName("time-out_mins") val timeOutMinutes: Long, // Time-out duration in minutes
    @SerializedName("total_hrs_work") val totalHoursWorked: Long, // Total hours worked
    @SerializedName("total_undertime") val totalUndertime: Long // Total undertime in minutes
)
