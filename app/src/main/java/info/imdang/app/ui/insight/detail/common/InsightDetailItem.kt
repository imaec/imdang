package info.imdang.app.ui.insight.detail.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T500_16_22_4
import info.imdang.resource.R

@Composable
fun InsightDetailItem(
    title: String,
    content: String,
    @DrawableRes icon: Int? = null
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        InsightDetailTitle(icon = icon, title = title)
        Text(
            text = content,
            style = T500_16_22_4,
            color = Gray900
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InsightDetailItemPreview() {
    ImdangTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            InsightDetailItem(
                icon = R.drawable.ic_pin_gray,
                title = stringResource(R.string.complex_address),
                content = "서울특별시 영등포구 당산 2동 123-467\n(당산아이파크1차)"
            )
            InsightDetailItem(
                icon = R.drawable.ic_calendar,
                title = stringResource(R.string.visit_date_and_time),
                content = "2024.01.01 / 저녁"
            )
            InsightDetailItem(
                icon = R.drawable.ic_car,
                title = stringResource(R.string.traffic_method),
                content = "자차, 도보"
            )
            InsightDetailItem(
                icon = R.drawable.ic_info,
                title = stringResource(R.string.access_limit),
                content = "자유로움"
            )
            InsightDetailItem(
                title = stringResource(R.string.insight_summary),
                content = "단지는 전반적으로 관리 상태가 양호했으며," +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
                    "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다." +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
                    "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다." +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요."
            )
        }
    }
}
