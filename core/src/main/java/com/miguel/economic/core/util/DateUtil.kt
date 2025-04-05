package com.miguel.economic.core.util

import android.annotation.SuppressLint
import com.miguel.economic.core.util.DateUtil.viewDateTimeFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

@SuppressLint("NewApi")
object DateUtil {
    val viewDateTimeFormat: DateTimeFormatter by lazy {
        DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .toFormatter()
    }
}

@get:SuppressLint("NewApi")
val LocalDateTime.viewFormat get() = format(viewDateTimeFormat)
