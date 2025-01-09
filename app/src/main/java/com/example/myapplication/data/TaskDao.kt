package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // タスクを挿入
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    // 全タスクを取得
    @Query("SELECT * FROM user_tasks WHERE date = :calendarDate")
    fun getAllTasks(calendarDate: String?): Flow<List<Task>>

    // 特定のタスクを削除
    @Delete
    suspend fun delete(task: Task)

    @Query("UPDATE user_tasks set title = :title, startTime = :startTime, endTime = :endTime WHERE id = :id")
    suspend fun update(id: Int?, title: String?, startTime: String?, endTime: String?)

}