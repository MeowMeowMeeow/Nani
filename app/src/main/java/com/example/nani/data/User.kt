package com.example.nani.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

//change for the API
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String
)

@Serializable
data class UserLogs(
    val _id: Int,
    val time_in: String,
    val totalHours: String,
    val time_out:String,
    val date: String,
    val status: String,
)



