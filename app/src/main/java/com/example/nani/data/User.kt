package com.example.nani.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//change for the API
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String
)



data class UserLogs(
    @SerializedName("_id") val id: String?,
    @SerializedName("User_id") val userId: String?,
    @SerializedName("Attendance Status") val attendanceStatus: String?,
    @SerializedName("Total Hours 100%") val totalHours: Int?,
    @SerializedName("Created By") val createdBy: String?,
    @SerializedName("time-in") val timeIn: Long?,
    @SerializedName("time-out") val timeOut: Long?,
    @SerializedName("Date") val date: Long?,
    @SerializedName("Modified Date") val modifiedDate: Long?,
    @SerializedName("Created Date") val createdDate: Long?,
    @SerializedName("toggle") val toggle: String?
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
