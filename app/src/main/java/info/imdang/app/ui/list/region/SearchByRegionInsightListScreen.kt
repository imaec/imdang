package info.imdang.app.ui.list.region

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import info.imdang.app.ui.insight.InsightItem
import info.imdang.app.ui.insight.InsightItemType
import info.imdang.app.ui.insight.detail.INSIGHT_DETAIL_SCREEN
import info.imdang.app.ui.list.region.preview.FakeSearchByRegionInsightListViewModel
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T600_16_22_4

const val SEARCH_BY_REGION_INSIGHT_LIST_SCREEN = "searchByRegionInsightList"

fun NavGraphBuilder.searchByRegionInsightListScreen(navController: NavController) {
    composable(
        route = SEARCH_BY_REGION_INSIGHT_LIST_SCREEN +
            "?siGunGu={siGunGu}" +
            "&eupMyeonDong={eupMyeonDong}"
    ) {
        SearchByRegionInsightListScreen(
            navController = navController,
            viewModel = hiltViewModel()
        )
    }
}

@Composable
private fun SearchByRegionInsightListScreen(
    navController: NavController,
    viewModel: SearchByRegionInsightListViewModel
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = viewModel.title.collectAsStateWithLifecycle().value,
                onClickBack = {
                    navController.popBackStack()
                }
            )
        },
        content = { contentPadding ->
            SearchByRegionInsightListContent(
                navController = navController,
                viewModel = viewModel,
                contentPadding = contentPadding
            )
        }
    )
}

@Composable
private fun SearchByRegionInsightListContent(
    navController: NavController,
    viewModel: SearchByRegionInsightListViewModel,
    contentPadding: PaddingValues
) {
    val insights = viewModel.insights.collectAsLazyPagingItems()
    val insightCount by viewModel.insightCount.collectAsStateWithLifecycle()

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
            text = "${insightCount}개",
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
            items(insights.itemCount) { index ->
                val insightVo = insights[index] ?: return@items
                InsightItem(
                    itemType = InsightItemType.HORIZONTAL,
                    coverImage = insightVo.mainImage,
                    region = insightVo.address.toGuDong(),
                    recommendCount = insightVo.recommendedCount,
                    title = insightVo.title,
                    nickname = insightVo.nickname,
                    onClick = {
                        navController.navigate(
                            "$INSIGHT_DETAIL_SCREEN?insightId=${insightVo.insightId}"
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchByRegionInsightListScreenPreview() {
    ImdangTheme {
        SearchByRegionInsightListScreen(
            navController = rememberNavController(),
            viewModel = FakeSearchByRegionInsightListViewModel()
        )
    }
}
