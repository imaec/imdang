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
import info.imdang.app.model.insight.ComplexFacilityVo
import info.imdang.app.ui.insight.detail.common.InsightDetailItem
import info.imdang.app.ui.insight.detail.common.InsightDetailSelectionItem
import info.imdang.app.ui.insight.write.ComplexFacilityItems
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R

@Composable
fun InsightDetailComplexFacilityItems(complexFacilityVo: ComplexFacilityVo) {
    Column {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            InsightDetailSelectionItem(
                title = stringResource(R.string.family),
                items = complexFacilityVo.familyFacilities
            )
            InsightDetailSelectionItem(
                title = stringResource(R.string.multipurpose),
                items = complexFacilityVo.multipurposeFacilities
            )
            InsightDetailSelectionItem(
                title = stringResource(R.string.leisure),
                items = complexFacilityVo.leisureFacilities
            )
            InsightDetailSelectionItem(
                title = stringResource(R.string.environment),
                items = complexFacilityVo.environments
            )
            if (complexFacilityVo.complexFacilityReview.isNotBlank()) {
                InsightDetailItem(
                    title = stringResource(R.string.complex_facility_overall_review),
                    content = complexFacilityVo.complexFacilityReview
                )
            }
        }
        HorizontalDivider(thickness = 8.dp, color = Gray50)
    }
}

@Preview(showBackground = true)
@Composable
private fun InsightDetailComplexFacilityItemsPreview() {
    ImdangTheme {
        InsightDetailComplexFacilityItems(
            ComplexFacilityVo(
                familyFacilities = ComplexFacilityItems.familyFacilities().map { it.name },
                multipurposeFacilities = ComplexFacilityItems.multipurposeFacilities()
                    .map { it.name },
                leisureFacilities = ComplexFacilityItems.leisureFacilities().map { it.name },
                environments = ComplexFacilityItems.environments().map { it.name },
                complexFacilityReview = "단지는 전반적으로 관리 상태가 양호했으며," +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
                    "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다." +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
                    "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다." +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요."
            )
        )
    }
}
