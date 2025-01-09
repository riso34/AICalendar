package com.example.myapplication.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow


class TaskRepository(private val taskDao: TaskDao) {

    //　タスクの取得
    fun getAllTasks(calendarDate: String) = taskDao.getAllTasks(calendarDate)

    // タスクを挿入
    suspend fun insertTask(task: Task) = taskDao.insert(task)

    // タスクを削除
    suspend fun deleteTask(task: Task) = taskDao.delete(task)

    // タスクを更新
    suspend fun updateTask(task: Task) = taskDao.update(task.id, task.title, task.startTime, task.endTime)
}