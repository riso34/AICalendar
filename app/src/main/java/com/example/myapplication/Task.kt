package com.example.myapplication

import java.util.Date

// Task.kt
data class Task(
    val id: Long = 0,
    val title: String,
    val description: String,
    val date: Date,
    val completed: Boolean = false
)