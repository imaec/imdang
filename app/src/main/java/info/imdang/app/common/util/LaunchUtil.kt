package info.imdang.app.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import info.imdang.app.const.NAVER_MAP_URL
import info.imdang.app.const.NAVER_MAP_WEB_URL
import java.util.Locale

fun launchNaverMap(
    context: Context,
    name: String,
    latitude: Double,
    longitude: Double
) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(String.format(Locale.KOREA, NAVER_MAP_URL, latitude, longitude, name))
    ).apply {
        addCategory(Intent.CATEGORY_BROWSABLE)
    }

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        launchNaverMapInBrowser(
            context = context,
            name = name,
            latitude = latitude,
            longitude = longitude
        )
    }
}

private fun launchNaverMapInBrowser(
    context: Context,
    name: String,
    latitude: Double,
    longitude: Double
) {
    context.startActivity(
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse(String.format(Locale.KOREA, NAVER_MAP_WEB_URL, latitude, longitude, name))
        )
    )
}
