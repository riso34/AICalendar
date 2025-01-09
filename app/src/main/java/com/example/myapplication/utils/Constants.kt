package com.example.myapplication.utils

class Constants {

    companion object {

        //時間をフォーマット関数
        fun formatTime(hour: Int, minute: Int): String {
            return String.format("%02d:%02d", hour, minute)
        }

        // 日付のフォーマット関数
        fun formatDate(year: Int, month: Int, dayOfMonth: Int): String {
            return String.format("%04d/%02d/%02d", year, month + 1, dayOfMonth)
        }
    }
}