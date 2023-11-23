package com.example.newsapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val UTC = "UTC"
const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val STANDARD_DATE_FORMAT = "dd-MM-yyyy"
const val STANDARD_TIME_FORMAT = "hh:mm a"

fun String.convertIsoDateToTimeInMillis(): Long? {
    val dateFormat = SimpleDateFormat(ISO_DATE_FORMAT, Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone(UTC)
    val date = dateFormat.parse(this)
    return date?.time
}

fun Long.convertToDateInStandardFormat(): String? {
    val dateFormat = SimpleDateFormat(STANDARD_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(Date(this))
}

fun Long.convertToStandardTime(): String? {
    val dateFormat = SimpleDateFormat(STANDARD_TIME_FORMAT, Locale.getDefault())
    return dateFormat.format(Date(this))
}