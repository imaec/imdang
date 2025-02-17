package info.imdang.component.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import info.imdang.resource.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_thin, FontWeight.W100),
    Font(R.font.pretendard_extralight, FontWeight.W200),
    Font(R.font.pretendard_light, FontWeight.W300),
    Font(R.font.pretendard_regular, FontWeight.W400),
    Font(R.font.pretendard_medium, FontWeight.W500),
    Font(R.font.pretendard_semibold, FontWeight.W600),
    Font(R.font.pretendard_bold, FontWeight.W700),
    Font(R.font.pretendard_extrabold, FontWeight.W800),
    Font(R.font.pretendard_black, FontWeight.W900)
)

val Typography = Typography().run {
    copy(
        displayLarge = displayLarge.copy(fontFamily = Pretendard),
        displayMedium = displayMedium.copy(fontFamily = Pretendard),
        displaySmall = displaySmall.copy(fontFamily = Pretendard),
        headlineLarge = headlineLarge.copy(fontFamily = Pretendard),
        headlineMedium = headlineMedium.copy(fontFamily = Pretendard),
        headlineSmall = headlineSmall.copy(fontFamily = Pretendard),
        titleLarge = titleLarge.copy(fontFamily = Pretendard),
        titleMedium = titleMedium.copy(fontFamily = Pretendard),
        titleSmall = titleSmall.copy(fontFamily = Pretendard),
        bodyLarge = bodyLarge.copy(fontFamily = Pretendard),
        bodyMedium = bodyMedium.copy(fontFamily = Pretendard),
        bodySmall = bodySmall.copy(fontFamily = Pretendard),
        labelLarge = labelLarge.copy(fontFamily = Pretendard),
        labelMedium = labelMedium.copy(fontFamily = Pretendard),
        labelSmall = labelSmall.copy(fontFamily = Pretendard)
    )
}

val T400_14_19_6 = TextStyle(
    color = Gray900,
    fontSize = 14.sp,
    fontWeight = FontWeight.W400,
    fontFamily = Pretendard,
    lineHeight = 19.6.sp
)

val T400_16_22_4 = TextStyle(
    color = Gray900,
    fontSize = 16.sp,
    fontWeight = FontWeight.W400,
    fontFamily = Pretendard,
    lineHeight = 22.4.sp
)

val T500_12_16_8 = TextStyle(
    color = Gray900,
    fontSize = 12.sp,
    fontWeight = FontWeight.W500,
    fontFamily = Pretendard,
    lineHeight = 16.8.sp
)

val T500_14_19_6 = TextStyle(
    color = Gray900,
    fontSize = 14.sp,
    fontWeight = FontWeight.W500,
    fontFamily = Pretendard,
    lineHeight = 19.6.sp
)

val T500_16_22_4 = TextStyle(
    color = Gray900,
    fontSize = 16.sp,
    fontWeight = FontWeight.W500,
    fontFamily = Pretendard,
    lineHeight = 22.4.sp
)

val T600_12_16_8 = TextStyle(
    color = Gray900,
    fontSize = 12.sp,
    fontWeight = FontWeight.W600,
    fontFamily = Pretendard,
    lineHeight = 16.8.sp
)

val T600_14_19_6 = TextStyle(
    color = Gray900,
    fontSize = 14.sp,
    fontWeight = FontWeight.W600,
    fontFamily = Pretendard,
    lineHeight = 19.6.sp
)

val T600_15_21_88 = TextStyle(
    color = Gray900,
    fontSize = 15.sp,
    fontWeight = FontWeight.W600,
    fontFamily = Pretendard,
    lineHeight = 21.88.sp
)

val T600_16_22_4 = TextStyle(
    color = Gray900,
    fontSize = 16.sp,
    fontWeight = FontWeight.W600,
    fontFamily = Pretendard,
    lineHeight = 22.4.sp
)

val T600_18_25_2 = TextStyle(
    color = Gray900,
    fontSize = 18.sp,
    fontWeight = FontWeight.W600,
    fontFamily = Pretendard,
    lineHeight = 25.2.sp
)

val T600_20_28 = TextStyle(
    color = Gray900,
    fontSize = 20.sp,
    fontWeight = FontWeight.W600,
    fontFamily = Pretendard,
    lineHeight = 28.sp
)

val T600_24_33_6 = TextStyle(
    color = Gray900,
    fontSize = 24.sp,
    fontWeight = FontWeight.W600,
    fontFamily = Pretendard,
    lineHeight = 33.6.sp
)

val T700_20_28 = TextStyle(
    color = Gray900,
    fontSize = 20.sp,
    fontWeight = FontWeight.W700,
    fontFamily = Pretendard,
    lineHeight = 28.sp
)

val T700_22_30_8 = TextStyle(
    color = Gray900,
    fontSize = 22.sp,
    fontWeight = FontWeight.W700,
    fontFamily = Pretendard,
    lineHeight = 30.8.sp
)

val T700_24_33_6 = TextStyle(
    color = Gray900,
    fontSize = 24.sp,
    fontWeight = FontWeight.W700,
    fontFamily = Pretendard,
    lineHeight = 33.6.sp
)

val T700_26_36_4 = TextStyle(
    color = Gray900,
    fontSize = 26.sp,
    fontWeight = FontWeight.W700,
    fontFamily = Pretendard,
    lineHeight = 36.4.sp
)
