package info.imdang.app.common.util

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

fun reformatDate(date: String): String {
    var newDate = date.replace(".", "")
    if (newDate.length > 4) {
        newDate = newDate.substring(0, 4) + "." + newDate.substring(4)
    }
    if (newDate.length > 7) {
        newDate = newDate.substring(0, 7) + "." + newDate.substring(7)
    }
    return newDate
}

fun nowDateTimeToString(format: String): String =
    LocalDateTime.now().format(DateTimeFormatter.ofPattern(format))

fun Long.toLocalDateTime(): LocalDateTime = LocalDateTime
    .ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())

fun Long.toLocalDate(): LocalDate = toLocalDateTime().toLocalDate()

fun Long.diffDays(): Int {
    val now = LocalDateTime.now()
    return Duration.between(toLocalDateTime(), now).toDays().toInt()
}

fun LocalDate.isToday() = isEqual(LocalDate.now())

fun String.isToday() =
    LocalDate.parse(this, DateTimeFormatter.ofPattern(getServerDefaultDateFormat())).isToday()

fun String.formatDate(fromFormat: String, toFormat: String): String {
    val dateTime = LocalDate.parse(this, DateTimeFormatter.ofPattern(fromFormat))
    return dateTime.format(DateTimeFormatter.ofPattern(toFormat))
}

fun String.formatBeforeText(): String {
    val dateTime = LocalDateTime.parse(
        this,
        DateTimeFormatter.ofPattern(getServerDefaultDateFormat())
    )
    val currentDateTime = LocalDateTime.now()
    val duration = Duration.between(currentDateTime, dateTime)
    val durationYears =
        ChronoUnit.YEARS.between(dateTime.toLocalDate(), currentDateTime.toLocalDate())
    val durationMonths =
        ChronoUnit.MONTHS.between(dateTime.toLocalDate(), currentDateTime.toLocalDate())
    val durationDays = duration.toDays().absoluteValue
    val durationHours = duration.toHours().absoluteValue
    val durationMinutes = duration.toMinutes().absoluteValue

    return when {
        durationYears > 0 -> "${durationYears}년 전"
        durationMonths > 0 -> "${durationMonths}개월 전"
        durationDays >= 7 -> "${durationDays / 7}주 전"
        durationDays == 1L -> "어제"
        durationDays > 0 -> "${durationDays}일 전"
        durationHours > 0 -> "${durationHours}시간 전"
        durationMinutes > 0 -> "${durationMinutes}분 전"
        else -> return "방금 전"
    }
}

private fun String.getServerDefaultDateFormat() =
    "yyyy-MM-dd'T'HH:mm:ss." + slice(20..lastIndex)
        .replace(Regex("\\d"), "S")
        .replace("Z", "X")
