package info.imdang.app.util

import java.util.Calendar

fun String.validateNickname(): String {
    return when (length) {
        0 -> "닉네임을 입력해주세요"
        1 -> "2자-10자로 작성해주세요"
        else -> ""
    }
}

fun String.validateBirthDate(): String {
    if (isEmpty()) return "생년월일을 입력해주세요"
    if (!matches(Regex("\\d{4}\\.\\d{2}\\.\\d{2}"))) return "잘못된 생년월일이에요"

    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH) + 1
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    val parts = split(".")
    val year = parts[0].toIntOrNull() ?: return "잘못된 생년월일이에요"
    val month = parts[1].toIntOrNull() ?: return "잘못된 생년월일이에요"
    val day = parts[2].toIntOrNull() ?: return "잘못된 생년월일이에요"
    if (month !in 1..12) return "잘못된 생년월일이에요"
    val maxDays = when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> {
            return "잘못된 생년월일이에요"
        }
    }
    if (day !in 1..maxDays) return "잘못된 생년월일이에요"

    if (year !in 1900..currentYear) return "잘못된 생년월일이에요"
    if (year == currentYear) {
        if (month > currentMonth || (month == currentMonth && day > currentDay)) {
            return "과거 또는 오늘 날짜를 입력해주세요"
        }
    }

    return ""
}

fun String.validateVisitDate(): String {
    if (!matches(Regex("\\d{4}\\.\\d{2}\\.\\d{2}"))) return "올바른 형식이 아니에요"

    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH) + 1
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    val parts = split(".")
    val year = parts[0].toIntOrNull() ?: return "올바른 형식이 아니에요"
    val month = parts[1].toIntOrNull() ?: return "올바른 형식이 아니에요"
    val day = parts[2].toIntOrNull() ?: return "올바른 형식이 아니에요"
    if (month !in 1..12) return "올바른 형식이 아니에요"
    val maxDays = when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> {
            return "올바른 형식이 아니에요"
        }
    }
    if (day !in 1..maxDays) return "올바른 형식이 아니에요"

    if (year < 2020) return "2020년 이전에 다녀온 임장은 작성할 수 없어요"
    if (year > currentYear) return "과거 또는 오늘 날짜를 입력해주세요"
    if (year == currentYear) {
        if (month > currentMonth || (month == currentMonth && day > currentDay)) {
            return "과거 또는 오늘 날짜를 입력해주세요"
        }
    }

    return ""
}

private fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}
