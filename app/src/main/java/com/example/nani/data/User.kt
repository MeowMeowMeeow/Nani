package com.example.nani.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//change for the API
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String
)



