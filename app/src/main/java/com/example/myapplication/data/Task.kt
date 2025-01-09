package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.*

@Entity(tableName = "user_tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val date: String?,
    val startTime: String,
    val endTime: String,
    val context: String,
    val notification: Boolean
)