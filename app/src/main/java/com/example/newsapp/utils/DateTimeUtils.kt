package com.example.newsapp.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

private const val UTC = "UTC"
const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

fun String.convertIsoDateToTimeInMillis(): Long? {
    val dateFormat = SimpleDateFormat(ISO_DATE_FORMAT, Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone(UTC)
    val date = dateFormat.parse(this)
    return date?.time
}