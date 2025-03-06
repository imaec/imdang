package info.imdang.app.ui.insight.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.app.model.insight.ComplexEnvironmentVo
import info.imdang.app.ui.insight.detail.common.InsightDetailItem
import info.imdang.app.ui.insight.detail.common.InsightDetailSelectionItem
import info.imdang.app.ui.insight.write.ComplexEnvironmentItems
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R

@Composable
fun InsightDetailComplexEnvironmentItems(complexEnvironmentVo: ComplexEnvironmentVo) {
    Column {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            InsightDetailSelectionItem(
                title = stringResource(R.string.building),
                example = stringResource(R.string.building_example),
                items = listOf(complexEnvironmentVo.building)
            )
            InsightDetailSelectionItem(
                title = stringResource(R.string.safety),
                example = stringResource(R.string.safety_example),
                items = listOf(complexEnvironmentVo.safety)
            )
            InsightDetailSelectionItem(
                title = stringResource(R.string.children_facility),
                example = stringResource(R.string.children_facility_example),
                items = listOf(complexEnvironmentVo.childrenFacility)
            )
            InsightDetailSelectionItem(
                title = stringResource(R.string.silver_facility),
                items = listOf(complexEnvironmentVo.silverFacility)
            )
            if (complexEnvironmentVo.complexEnvironmentReview.isNotBlank()) {
                InsightDetailItem(
                    title = stringResource(R.string.complex_environment_overall_review),
                    content = complexEnvironmentVo.complexEnvironmentReview
                )
            }
        }
        HorizontalDivider(thickness = 8.dp, color = Gray50)
    }
}

@Preview(showBackground = true)
@Composable
private fun InsightDetailComplexEnvironmentItemsPreview() {
    ImdangTheme {
        InsightDetailComplexEnvironmentItems(
            ComplexEnvironmentVo(
                building = ComplexEnvironmentItems.buildings().first().name,
                safety = ComplexEnvironmentItems.safeties().first().name,
                childrenFacility = ComplexEnvironmentItems.childrenFacilities().first().name,
                silverFacility = ComplexEnvironmentItems.silverFacilities().first().name,
                complexEnvironmentReview = "단지는 전반적으로 관리 상태가 양호했으며," +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
                    "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다." +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
                    "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다." +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요."
            )
        )
    }
}
