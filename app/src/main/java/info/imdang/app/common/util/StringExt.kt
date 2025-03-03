package info.imdang.app.common.util

fun String.snakeToCamelCase(): String {
    return "_[a-zA-Z]".toRegex().replace(lowercase()) {
        it.value.replace("_", "")
            .uppercase()
    }
}
