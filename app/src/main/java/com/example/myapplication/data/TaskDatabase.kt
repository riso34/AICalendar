package com.example.myapplication.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
//import com.example.myapplication.utils.DATABASE_NAME

@Database(entities = [Task::class], version = 3, exportSchema = false)
// @TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "tasks"
                )
                    .fallbackToDestructiveMigration() // データを削除して新しいスキーマを生成する
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
