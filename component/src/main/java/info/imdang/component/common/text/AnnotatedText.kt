package info.imdang.component.common.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T400_14_19_6
import info.imdang.component.theme.T600_14_19_6
import info.imdang.component.theme.T600_18_25_2

@Composable
fun AnnotatedText(
    text: String,
    pointText: String,
    style: TextStyle = LocalTextStyle.current,
    pointStyle: TextStyle = style,
    color: Color = Gray900,
    pointColor: Color = Orange500
) {
    val pointIndex = text.indexOf(pointText)
    if (pointIndex == -1) return Text(text = text, style = style, color = color)

    AnnotatedText(
        text = text,
        pointText = pointText,
        pointIndex = pointIndex,
        style = style,
        pointStyle = pointStyle,
        color = color,
        pointColor = pointColor
    )
}

@Composable
fun AnnotatedText(
    text: String,
    pointText: String,
    pointIndex: Int,
    style: TextStyle = LocalTextStyle.current,
    pointStyle: TextStyle = style,
    color: Color = Gray900,
    pointColor: Color = Orange500
) {
    val previousText = text.slice(0..<pointIndex)
    val nextText = text.slice((pointIndex + pointText.length)..text.lastIndex)

    val annotatedString = buildAnnotatedString {
        append(previousText)
        withStyle(style = pointStyle.toSpanStyle().copy(color = pointColor)) {
            append(pointText)
        }
        append(nextText)
    }

    Text(text = annotatedString, style = style, color = color)
}

@Preview(showBackground = true)
@Composable
private fun AnnotatedTextPreview() {
    ImdangTheme {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            AnnotatedText(
                text = "아파트 임당",
                pointText = "아파트",
                style = T600_18_25_2,
                color = Gray900
            )
            AnnotatedText(
                text = "아파트 임당",
                pointText = "임당",
                style = T600_18_25_2,
                color = Gray900
            )
            AnnotatedText(
                text = "아파트 임당",
                pointText = "트 임",
                style = T600_18_25_2,
                color = Gray900
            )
            AnnotatedText(
                text = "1 / 4",
                pointText = "1",
                pointIndex = 0,
                style = T400_14_19_6,
                pointStyle = T600_14_19_6,
                color = Gray700,
                pointColor = Orange500
            )
            AnnotatedText(
                text = "2 / 4",
                pointText = "2",
                pointIndex = 0,
                style = T400_14_19_6,
                pointStyle = T600_14_19_6,
                color = Gray700,
                pointColor = Orange500
            )
        }
    }
}
