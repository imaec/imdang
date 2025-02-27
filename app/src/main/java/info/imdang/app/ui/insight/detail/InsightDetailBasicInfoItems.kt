package info.imdang.app.ui.insight.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.app.ui.insight.detail.common.InsightDetailItem
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R

@Composable
fun InsightDetailBasicInfoItems(
    address: String,
    complexName: String,
    visitDate: String,
    visitTimes: List<String>,
    trafficMethods: List<String>,
    accessLimit: String,
    summary: String
) {
    Column {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            InsightDetailItem(
                icon = R.drawable.ic_pin_gray,
                title = stringResource(R.string.complex_address),
                content = "$address\n($complexName)"
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(width = 1.dp, color = Gray100, shape = RoundedCornerShape(8.dp))
            )
            InsightDetailItem(
                icon = R.drawable.ic_calendar,
                title = stringResource(R.string.visit_date_and_time),
                content = "$visitDate / ${visitTimes.joinToString(", ")}"
            )
            InsightDetailItem(
                icon = R.drawable.ic_car,
                title = stringResource(R.string.traffic_method),
                content = trafficMethods.joinToString(", ")
            )
            InsightDetailItem(
                icon = R.drawable.ic_info,
                title = stringResource(R.string.access_limit),
                content = accessLimit
            )
            InsightDetailItem(
                title = stringResource(R.string.insight_summary),
                content = summary
            )
        }
        HorizontalDivider(thickness = 8.dp, color = Gray50)
    }
}

@Preview(showBackground = true)
@Composable
private fun InsightDetailBasicInfoItemsPreview() {
    ImdangTheme {
        InsightDetailBasicInfoItems(
            address = "서울특별시 영등포구 당산 2동 123-467",
            complexName = "당산아이파크1차",
            visitDate = "2024.01.01",
            visitTimes = listOf("아침", "저녁"),
            trafficMethods = listOf("자차", "도보"),
            accessLimit = "자유로움",
            summary = "단지는 전반적으로 관리 상태가 양호했으며," +
                " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
                "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다." +
                " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
                "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다." +
                " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요."
        )
    }
}
