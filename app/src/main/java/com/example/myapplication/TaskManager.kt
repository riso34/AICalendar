package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import java.util.Calendar
import java.util.Date

// TaskManager.kt
class TaskManager(private val context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun addTask(task: Task): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("title", task.title)
            put("description", task.description)
            put("date", task.date.time)
            put("completed", if (task.completed) 1 else 0)
        }
        return db.insert("tasks", null, values)
    }
    fun getTasksForDate(year: Int, month: Int, dayOfMonth: Int): List<Task> {
        val db = dbHelper.readableDatabase
        val calendar = Calendar.getInstance().apply {
            set(year, month, dayOfMonth, 0, 0, 0)
        }
        val startOfDay = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val endOfDay = calendar.timeInMillis

        val cursor = db.query(
            "tasks",
            null,
            "date >= ? AND date < ?",
            arrayOf(startOfDay.toString(), endOfDay.toString()),
            null,
            null,
            "date ASC"
        )

        val tasks = mutableListOf<Task>()
        with(cursor) {
            while (moveToNext()) {
                tasks.add(
                    Task(
                        id = getLong(getColumnIndexOrThrow("_id")),
                        title = getString(getColumnIndexOrThrow("title")),
                        description = getString(getColumnIndexOrThrow("description")),
                        date = Date(getLong(getColumnIndexOrThrow("date"))),
                        completed = getInt(getColumnIndexOrThrow("completed")) == 1
                    )
                )
            }
        }
        cursor.close()
        return tasks
    }
}