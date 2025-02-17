package info.imdang.ui.list.new

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T600_16_22_4
import info.imdang.resource.R
import info.imdang.ui.insight.InsightItem
import info.imdang.ui.insight.InsightItemType

const val NEW_INSIGHT_LIST_SCREEN = "newInsight"

fun NavGraphBuilder.newInsightScreen(navController: NavController) {
    composable(route = NEW_INSIGHT_LIST_SCREEN) {
        NewInsightScreen(navController = navController)
    }
}

@Composable
private fun NewInsightScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.home_new_insight_subject),
                onClickBack = {
                    navController.popBackStack()
                }
            )
        },
        content = { contentPadding ->
            NewInsightContent(contentPadding)
        }
    )
}

@Composable
private fun NewInsightContent(contentPadding: PaddingValues) {
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
        Text(
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 20.dp),
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
private fun NewInsightScreenPreview() {
    ImdangTheme {
        NewInsightScreen(navController = rememberNavController())
    }
}
