package info.imdang.app.common.util

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
