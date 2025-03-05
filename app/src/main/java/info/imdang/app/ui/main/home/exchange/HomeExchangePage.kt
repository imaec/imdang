package info.imdang.app.ui.main.home.exchange

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import info.imdang.app.model.insight.ExchangeRequestStatus
import info.imdang.app.model.insight.ExchangeType
import info.imdang.app.ui.insight.InsightItem
import info.imdang.app.ui.insight.InsightItemType
import info.imdang.app.ui.insight.detail.INSIGHT_DETAIL_SCREEN
import info.imdang.app.ui.main.home.HomeViewModel
import info.imdang.app.ui.main.home.preview.FakeHomeViewModel
import info.imdang.component.common.image.Icon
import info.imdang.component.system.chip.CommonChip
import info.imdang.component.system.tab.Tabs
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray25
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray600
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T500_14_19_6
import info.imdang.component.theme.T600_16_22_4
import info.imdang.resource.R
import kotlinx.coroutines.launch

@Composable
fun HomeExchangePage(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val pagerState = rememberPagerState { 2 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray25)
    ) {
        HorizontalDivider(color = Gray100)
        Spacer(modifier = Modifier.height(24.dp))
        OwnedPassView(viewModel = viewModel)
        Spacer(modifier = Modifier.height(12.dp))
        ExchangeTabRow(
            viewModel = viewModel,
            pagerState = pagerState
        )
        HorizontalPager(state = pagerState) {
            ExchangeInsightListPage(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun OwnedPassView(viewModel: HomeViewModel) {
    val couponCount = viewModel.coupon.collectAsStateWithLifecycle().value?.couponCount ?: 0

    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(54.dp)
            .background(
                color = Orange50,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                iconResource = R.drawable.ic_pass,
                tint = Orange500
            )
            Text(
                text = stringResource(R.string.owned_pass),
                style = T600_16_22_4,
                color = Gray900
            )
        }
        Text(
            text = "${couponCount}개",
            style = T600_16_22_4,
            color = Orange500
        )
    }
}

@Composable
private fun ExchangeTabRow(
    viewModel: HomeViewModel,
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        stringResource(R.string.request_exchange_history),
        stringResource(R.string.requested_exchange_history)
    )

    LaunchedEffect(pagerState.currentPage) {
        viewModel.updateExchangeType(ExchangeType.fromId(pagerState.currentPage))
    }

    Tabs(
        selectedTabIndex = pagerState.currentPage,
        tabs = tabs,
        divider = { HorizontalDivider(color = Gray100) },
        onTabSelected = {
            coroutineScope.launch {
                pagerState.animateScrollToPage(it)
            }
        }
    )
}

@Composable
private fun ExchangeInsightListPage(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val currentExchangeType by viewModel.currentExchangeType.collectAsStateWithLifecycle()
    val selectedExchangeStatus by viewModel.selectedExchangeStatus.collectAsStateWithLifecycle()
    val exchangeStatus = listOf(
        ExchangeRequestStatus.PENDING to stringResource(R.string.waiting),
        ExchangeRequestStatus.REJECTED to stringResource(R.string.reject),
        ExchangeRequestStatus.ACCEPTED to stringResource(R.string.exchange_complete)
    )
    val insights = if (currentExchangeType == ExchangeType.REQUEST) {
        viewModel.requestExchangeInsights.collectAsLazyPagingItems()
    } else {
        viewModel.requestedExchangeInsights.collectAsLazyPagingItems()
    }
    val exchangeInsightCount by viewModel.exchangeInsightCount.collectAsStateWithLifecycle()

    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        LazyRow(
            modifier = Modifier.padding(top = 24.dp),
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            items(exchangeStatus) {
                val isSelected = it.first == selectedExchangeStatus
                val title = if (isSelected) {
                    "${it.second} ($exchangeInsightCount)"
                } else {
                    it.second
                }
                CommonChip(
                    text = title,
                    isSelected = isSelected,
                    onClick = {
                        viewModel.onClickExchangeStatus(it.first)
                    }
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .height(52.dp)
                .background(
                    color = Gray50,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                iconResource = R.drawable.ic_info,
                tint = Gray600
            )
            Text(
                text = stringResource(R.string.waiting_message),
                style = T500_14_19_6,
                color = Gray600
            )
        }
        LazyColumn(
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                bottom = 20.dp
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
                        navController.navigate(INSIGHT_DETAIL_SCREEN)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeExchangePagePreview() {
    ImdangTheme {
        HomeExchangePage(
            navController = rememberNavController(),
            viewModel = FakeHomeViewModel()
        )
    }
}
