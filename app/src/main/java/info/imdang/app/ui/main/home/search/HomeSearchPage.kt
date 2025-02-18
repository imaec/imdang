package info.imdang.app.ui.main.home.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.insight.InsightItem
import info.imdang.app.ui.insight.InsightItemType
import info.imdang.app.ui.list.new.NEW_INSIGHT_LIST_SCREEN
import info.imdang.app.ui.list.visitcomplex.VISIT_COMPLEX_INSIGHT_LIST_SCREEN
import info.imdang.app.ui.main.home.HomeViewModel
import info.imdang.app.ui.main.home.search.map.SEARCH_BY_MAP_SCREEN
import info.imdang.app.ui.main.home.search.region.SEARCH_BY_REGION_SCREEN
import info.imdang.component.common.image.Icon
import info.imdang.component.common.modifier.clickableWithoutRipple
import info.imdang.component.common.modifier.dashedBorder
import info.imdang.component.common.text.AnnotatedText
import info.imdang.component.system.chip.CommonChip
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray25
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray800
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T400_14_19_6
import info.imdang.component.theme.T500_14_19_6
import info.imdang.component.theme.T600_14_19_6
import info.imdang.component.theme.T600_16_22_4
import info.imdang.component.theme.T600_18_25_2
import info.imdang.component.theme.White
import info.imdang.resource.R
import kotlin.math.ceil

@Composable
fun HomeSearchPage(navController: NavController) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .collect {
                homeViewModel.hideTooltip()
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray25)
    ) {
        HomeSearchView(navController = navController)
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 56.dp)
        ) {
            item {
                HomeSearchBannerView()
            }
            item {
                HomeSearchVisitComplexView(navController = navController)
            }
            item {
                HomeSearchNewInsightsView(navController = navController)
            }
            item {
                HomeSearchRecommendInsightsView()
            }
        }
    }
}

@Composable
private fun HomeSearchView(navController: NavController) {
    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 8.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .border(
                        width = 1.dp,
                        color = Gray100,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate(SEARCH_BY_REGION_SCREEN)
                    }
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    iconResource = R.drawable.ic_search,
                    tint = Gray900
                )
                Text(
                    text = stringResource(R.string.home_search_hint),
                    style = T500_14_19_6,
                    color = Gray500
                )
            }
            Icon(
                modifier = Modifier
                    .background(color = Gray800, shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate(SEARCH_BY_MAP_SCREEN)
                    }
                    .padding(16.dp)
                    .size(18.dp),
                iconResource = R.drawable.ic_map,
                tint = White
            )
        }
        HorizontalDivider(color = Gray100)
    }
}

@Composable
private fun HomeSearchBannerView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Orange50)
            .clickable {
                // todo : 서비스 소개 화면으로 이동
            }
            .padding(all = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.home_banner_content),
            style = T600_16_22_4,
            color = Gray900
        )
        Icon(
            modifier = Modifier.size(56.dp),
            iconResource = R.drawable.ic_home_banner
        )
    }
}

@Composable
private fun HomeSearchVisitComplexView(navController: NavController) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val complexes = listOf("신논현 더 센트럴 푸르지오", "신논현 더 센트럴 푸르지오", "신논현 더 센트럴 푸르지오")
    val visitComplexInsights = listOf(
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기"
    )

    Column(modifier = Modifier.padding(top = 32.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.home_visit_complex_insight_subject),
                style = T600_18_25_2,
                color = Gray900
            )
            Text(
                modifier = Modifier.clickableWithoutRipple {
                    navController.navigate(VISIT_COMPLEX_INSIGHT_LIST_SCREEN)
                },
                text = stringResource(R.string.see_all),
                style = T400_14_19_6,
                color = Gray700
            )
        }
        if (complexes.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.padding(top = 16.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(complexes) { index, complex ->
                    CommonChip(
                        text = complex,
                        isSelected = index == 0,
                        onClick = {
                            homeViewModel.hideTooltip()
                            // todo : 칩 선택
                        }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                visitComplexInsights.take(3).forEach {
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
        } else {
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 24.dp)
            ) {
                VisitComplexInsightEmptyView()
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 32.dp),
            thickness = 8.dp,
            color = Gray50
        )
    }
}

@Composable
private fun VisitComplexInsightEmptyView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(
                color = Gray50,
                shape = RoundedCornerShape(8.dp)
            )
            .dashedBorder(
                width = 1.dp,
                radius = 8.dp,
                color = Gray100
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.empty_home_insight_message),
            style = T500_14_19_6,
            color = Gray500,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun HomeSearchNewInsightsView(navController: NavController) {
    val newInsights = listOf(
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기"
    )

    Column(modifier = Modifier.padding(top = 32.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.home_new_insight_subject),
                style = T600_18_25_2,
                color = Gray900
            )
            Text(
                modifier = Modifier.clickableWithoutRipple {
                    navController.navigate(NEW_INSIGHT_LIST_SCREEN)
                },
                text = stringResource(R.string.see_all),
                style = T400_14_19_6,
                color = Gray700
            )
        }
        LazyRow(
            modifier = Modifier.padding(top = 24.dp),
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(newInsights) { insight ->
                InsightItem(
                    itemType = InsightItemType.VERTICAL,
                    coverImage = "",
                    region = "강남구 신논현동",
                    recommendCount = 24,
                    title = insight,
                    nickname = "홍길동",
                    onClick = {
                        // todo : 인사이트 상세로 이동
                    }
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 32.dp),
            thickness = 8.dp,
            color = Gray50
        )
    }
}

@Composable
private fun HomeSearchRecommendInsightsView() {
    val recommendInsights = listOf(
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기",
        "초역세권 대단지 아파트 후기"
    )
    val page = ceil((recommendInsights.size / 3.0)).toInt()
    val pagerState = rememberPagerState { page }

    Column(
        modifier = Modifier.padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnnotatedText(
                text = stringResource(R.string.home_recommend_insight_subject),
                pointText = stringResource(R.string.home_recommend_insight_subject_point),
                style = T600_18_25_2,
                color = Gray900
            )
            AnnotatedText(
                text = "${pagerState.currentPage + 1} / 4",
                pointText = "${pagerState.currentPage + 1}",
                pointIndex = 0,
                style = T400_14_19_6,
                pointStyle = T600_14_19_6,
                color = Gray700,
                pointColor = Orange500
            )
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(top = 24.dp)
                .height(324.dp),
            verticalAlignment = Alignment.Top
        ) { index ->
            val insights = recommendInsights.chunked(3)[index]
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                insights.forEach {
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
        LazyRow(
            modifier = Modifier.padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(page) { index ->
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == pagerState.currentPage) Orange500 else Gray100
                        )
                )
            }
        }
    }
}

@Preview(heightDp = 1556)
@Composable
private fun HomeSearchPagePreview() {
    ImdangTheme {
        HomeSearchPage(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
private fun VisitComplexInsightEmptyViewPreview() {
    ImdangTheme {
        Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp)) {
            VisitComplexInsightEmptyView()
        }
    }
}
