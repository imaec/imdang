package info.imdang.ui.list.visitcomplex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.system.chip.CommonChip
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T600_16_22_4
import info.imdang.resource.R
import info.imdang.ui.insight.InsightItem
import info.imdang.ui.insight.InsightItemType

const val VISIT_COMPLEX_INSIGHT_LIST_SCREEN = "visitComplexInsight"

fun NavGraphBuilder.visitComplexInsightScreen(navController: NavController) {
    composable(route = VISIT_COMPLEX_INSIGHT_LIST_SCREEN) {
        VisitComplexInsightScreen(navController = navController)
    }
}

@Composable
private fun VisitComplexInsightScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.home_visit_complex_insight_subject),
                onClickBack = {
                    navController.popBackStack()
                }
            )
        },
        content = { contentPadding ->
            VisitComplexInsightContent(contentPadding)
        }
    )
}

@Composable
private fun VisitComplexInsightContent(contentPadding: PaddingValues) {
    val complexes = listOf("신논현 더 센트럴 푸르지오", "신논현 더 센트럴 푸르지오", "신논현 더 센트럴 푸르지오")
    val insights = mutableListOf<String>().apply {
        repeat(33) {
            add("초역세권 대단지 아파트 후기")
        }
    }

    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LazyRow(
            modifier = Modifier.padding(top = 24.dp),
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(complexes) { index, chip ->
                CommonChip(
                    text = chip,
                    isSelected = index == 0,
                    onClick = {
                        // todo : 칩 선택
                    }
                )
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "${insights.size}개",
            style = T600_16_22_4,
            color = Gray900
        )
        LazyColumn(
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                bottom = 12.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(insights) {
                InsightItem(
                    itemType = InsightItemType.HORIZONTAL,
                    coverImage = "",
                    region = "강남구 신논현동",
                    recommendCount = 24,
                    title = it,
                    nickname = "홍길동",
                    onClick = {
                        // todo : 인사이트 상세로 이동
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun VisitComplexInsightScreenPreview() {
    ImdangTheme {
        VisitComplexInsightScreen(navController = rememberNavController())
    }
}
