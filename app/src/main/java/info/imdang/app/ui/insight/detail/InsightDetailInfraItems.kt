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
import info.imdang.app.model.insight.InfraVo
import info.imdang.app.ui.insight.detail.common.InsightDetailItem
import info.imdang.app.ui.insight.detail.common.InsightDetailSelectionItem
import info.imdang.app.ui.insight.write.InfraItems
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R

@Composable
fun InsightDetailInfraItems(infra: InfraVo) {
    Column {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            InsightDetailSelectionItem(
                title = stringResource(R.string.traffic),
                items = infra.traffics
            )
            InsightDetailSelectionItem(
                title = stringResource(R.string.school),
                items = infra.schools
            )
            InsightDetailSelectionItem(
                title = stringResource(R.string.living_facility),
                items = infra.lifeFacilities
            )
            InsightDetailSelectionItem(
                title = stringResource(R.string.culture_facility),
                items = infra.cultureFacilities
            )
            InsightDetailSelectionItem(
                title = stringResource(R.string.surrounding_environment),
                items = infra.surroundingEnvironments
            )
            InsightDetailSelectionItem(
                title = stringResource(R.string.landmark),
                items = infra.landmarks
            )
            InsightDetailSelectionItem(
                title = stringResource(R.string.avoid_facility),
                items = infra.avoidFacilities
            )
            if (infra.infraReview.isNotBlank()) {
                InsightDetailItem(
                    title = stringResource(R.string.infra_overall_review),
                    content = infra.infraReview
                )
            }
        }
        HorizontalDivider(thickness = 8.dp, color = Gray50)
    }
}

@Preview(showBackground = true, heightDp = 1370)
@Composable
private fun InsightDetailInfraItemsPreview() {
    ImdangTheme {
        InsightDetailInfraItems(
            InfraVo(
                traffics = InfraItems.traffics().map { it.name },
                schools = InfraItems.schools().map { it.name },
                lifeFacilities = InfraItems.livingFacilities().map { it.name },
                cultureFacilities = InfraItems.cultureFacilities().map { it.name },
                surroundingEnvironments = InfraItems.surroundingEnvironments().map { it.name },
                landmarks = InfraItems.landmarks().map { it.name },
                avoidFacilities = InfraItems.avoidFacilities().map { it.name },
                infraReview = "단지는 전반적으로 관리 상태가 양호했으며," +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
                    "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다." +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
                    "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다." +
                    " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요."
            )
        )
    }
}
