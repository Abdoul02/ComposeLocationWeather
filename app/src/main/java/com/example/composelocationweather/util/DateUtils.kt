package com.example.composelocationweather.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {

    fun getDayOfWeekFromDate(dateTime: String): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date: Date? = dateFormat.parse(dateTime)
        val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())

        return date?.let { dayOfWeekFormat.format(it) }
    }

    fun getDate(milliSeconds: Long): String {
        val formatter = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.getDefault())
        val calender = Calendar.getInstance()
        calender.timeInMillis = milliSeconds * 1000L
        return formatter.format(calender.time)
    }
}