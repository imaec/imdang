package info.imdang.app.ui.insight.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.app.ui.insight.detail.common.InsightDetailItem
import info.imdang.app.ui.insight.detail.common.InsightDetailSelectionItem
import info.imdang.app.ui.insight.write.GoodNewsItems
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R

@Composable
fun InsightDetailGoodNewsItems(
    traffics: List<String>,
    developments: List<String>,
    educations: List<String>,
    naturalEnvironments: List<String>,
    cultures: List<String>,
    industries: List<String>,
    policies: List<String>,
    goodNewsReview: String
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        InsightDetailSelectionItem(
            title = stringResource(R.string.traffic),
            items = traffics
        )
        InsightDetailSelectionItem(
            title = stringResource(R.string.development),
            items = developments
        )
        InsightDetailSelectionItem(
            title = stringResource(R.string.education),
            items = educations
        )
        InsightDetailSelectionItem(
            title = stringResource(R.string.natural_environment),
            items = naturalEnvironments
        )
        InsightDetailSelectionItem(
            title = stringResource(R.string.culture),
            items = cultures
        )
        InsightDetailSelectionItem(
            title = stringResource(R.string.industry),
            items = industries
        )
        InsightDetailSelectionItem(
            title = stringResource(R.string.policy),
            items = policies
        )
        InsightDetailItem(
            title = stringResource(R.string.good_news_overall_review),
            content = goodNewsReview
        )
    }
}

@Preview(showBackground = true, heightDp = 1270)
@Composable
private fun InsightDetailGoodNewsItemsPreview() {
    ImdangTheme {
        InsightDetailGoodNewsItems(
            traffics = GoodNewsItems.traffics().map { it.name },
            developments = GoodNewsItems.developments().map { it.name },
            educations = GoodNewsItems.educations().map { it.name },
            naturalEnvironments = GoodNewsItems.naturalEnvironments().map { it.name },
            cultures = GoodNewsItems.cultures().map { it.name },
            industries = GoodNewsItems.industries().map { it.name },
            policies = GoodNewsItems.policies().map { it.name },
            goodNewsReview = "단지는 전반적으로 관리 상태가 양호했으며," +
                " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
                "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다." +
                " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
                "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다." +
                " 다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요."
        )
    }
}
