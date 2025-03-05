package info.imdang.app.ui.list.visitcomplex

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import info.imdang.app.ui.insight.InsightItem
import info.imdang.app.ui.insight.InsightItemType
import info.imdang.app.ui.insight.detail.INSIGHT_DETAIL_SCREEN
import info.imdang.app.ui.list.visitcomplex.preview.FakeVisitComplexInsightListViewModel
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.system.chip.CommonChip
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T600_16_22_4
import info.imdang.resource.R

const val VISIT_COMPLEX_INSIGHT_LIST_SCREEN = "visitComplexInsight"

fun NavGraphBuilder.visitComplexInsightScreen(navController: NavController) {
    composable(route = "$VISIT_COMPLEX_INSIGHT_LIST_SCREEN?selectedIndex={selectedIndex}") {
        VisitComplexInsightScreen(
            navController = navController,
            viewModel = hiltViewModel()
        )
    }
}

@Composable
private fun VisitComplexInsightScreen(
    navController: NavController,
    viewModel: VisitComplexInsightListViewModel
) {
    BackHandler {
        popBackStack(
            navController = navController,
            viewModel = viewModel
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.home_visit_complex_insight_subject),
                onClickBack = {
                    popBackStack(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            )
        },
        content = { contentPadding ->
            VisitComplexInsightContent(
                navController = navController,
                viewModel = viewModel,
                contentPadding = contentPadding
            )
        }
    )
}

private fun popBackStack(
    navController: NavController,
    viewModel: VisitComplexInsightListViewModel
) {
    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.set("selectedIndex", viewModel.selectedIndex.value)
    navController.popBackStack()
}

@Composable
private fun VisitComplexInsightContent(
    navController: NavController,
    viewModel: VisitComplexInsightListViewModel,
    contentPadding: PaddingValues
) {
    val complexes by viewModel.visitedComplexes.collectAsStateWithLifecycle()
    val insightCount by viewModel.insightCount.collectAsStateWithLifecycle()
    val insights = viewModel.insightsByComplex.collectAsLazyPagingItems()

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
            items(complexes) {
                CommonChip(
                    text = it.complexName,
                    isSelected = it.isSelected,
                    onClick = {
                        viewModel.onClickVisitedComplex(it)
                    }
                )
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
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
            items(
                count = insights.itemCount,
                key = insights.itemKey { it.insightId }
            ) { index ->
                val insightVo = insights[index] ?: return@items
                InsightItem(
                    itemType = InsightItemType.HORIZONTAL,
                    coverImage = insightVo.mainImage,
                    region = insightVo.address.toGuDong(),
                    recommendCount = insightVo.recommendedCount,
                    title = insightVo.title,
                    nickname = insightVo.nickname,
                    onClick = {
                        navController.navigate(INSIGHT_DETAIL_SCREEN)
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
        VisitComplexInsightScreen(
            navController = rememberNavController(),
            viewModel = FakeVisitComplexInsightListViewModel()
        )
    }
}
