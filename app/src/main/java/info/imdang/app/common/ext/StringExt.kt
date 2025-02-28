package info.imdang.app.common.ext

import java.net.URLEncoder

fun String.encodeUtf8(): String = URLEncoder.encode(this, Charsets.UTF_8.name())
