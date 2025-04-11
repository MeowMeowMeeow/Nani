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
//adjusted to be hardcoded data
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


//Not working for now since the API is down
data class TimeInRequest(
    @SerializedName("time-in") val timeIn: String,
    @SerializedName("date") val date: String,
    @SerializedName("attendance_status") val attendanceStatus: String,
    @SerializedName("time-in_mins") val timeInMins: Int,
    @SerializedName("total_late_mins") val totalLateMins: Int,
    @SerializedName("user_id") val userId: String
)



data class TimeInResponse(
    val status: String,
    val message: String
)

// Not working due to API
data class TimeOutRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("timeout") val timeOut: String,
    @SerializedName("time-out_mins") val timeOutMinutes: Long,
    @SerializedName("total_hrs_work") val totalHoursWorked: Long,
    @SerializedName("total_undertime") val totalUndertime: Long
)
