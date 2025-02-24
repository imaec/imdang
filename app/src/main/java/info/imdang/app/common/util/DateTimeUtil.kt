package info.imdang.app.common.util

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

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

fun nowDateTimeToString(format: String): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now().format(DateTimeFormatter.ofPattern(format))
    } else {
        SimpleDateFormat(format, Locale.KOREA).format(Date())
    }
}
