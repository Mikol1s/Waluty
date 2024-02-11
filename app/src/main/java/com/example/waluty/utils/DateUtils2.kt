package com.example.waluty.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


fun Date.toDateFormat(pattern: String = "yyyy-MM-dd"): String {
    val instance = Calendar.getInstance()
    instance.time = this
    val dateInstance = SimpleDateFormat(pattern, Locale.getDefault())
    val parse: String = try {
        dateInstance.format(instance.time) ?: run {
            Date(1970, 0, 1).toString()
        }
    } catch (exception: Exception) {
        throw exception
    }
    return parse
}

fun Date.toDate(
    offset: Int = 0,
): Date {
    val instance = Calendar.getInstance()
    instance.time = this
    instance.add(Calendar.DAY_OF_MONTH, offset)
    return instance.time
}

fun Date.toTimeFormat(pattern: String = "HH:mm:ss"): String {
    val currentTime = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return dateFormat.format(currentTime)
}

fun String.convertDateFormat(inputFormat: String, outputFormat: String): String {
    return try {
        val inputDateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
        val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())

        val parsedDate: Date =
            inputDateFormat.parse(this) ?: throw ParseException("Parsing failed", 0)

        outputDateFormat.format(parsedDate)
    } catch (e: Exception) {
        throw e
    }
}