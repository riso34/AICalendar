package com.example.myapplication.data

import androidx.room.TypeConverter
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.Date

class Converters {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd_HH:mm")

    @TypeConverter
    fun fromLocalTime(localTime: LocalTime?): String? {
        return localTime?.format(formatter)
    }

    @TypeConverter
    fun toLocalTime(timeString: String?): LocalTime? {
        return timeString?.let {
            LocalTime.parse(it, formatter)
        }
    }
}