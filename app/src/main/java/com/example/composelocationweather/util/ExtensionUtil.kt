package com.example.composelocationweather.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun String.toDayOfTheWeek(): String? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date: Date? = dateFormat.parse(this)
    val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())

    return date?.let { dayOfWeekFormat.format(it) }
}

fun Long.toDate(): String {
    val formatter = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.getDefault())
    val calender = Calendar.getInstance()
    calender.timeInMillis = this * 1000L
    return formatter.format(calender.time)
}

