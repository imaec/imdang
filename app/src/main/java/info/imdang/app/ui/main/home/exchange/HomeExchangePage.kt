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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.app.ui.insight.InsightItem
import info.imdang.app.ui.insight.InsightItemType
import info.imdang.component.common.image.Icon
import info.imdang.component.system.chip.CommonChip
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray25
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray500
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
fun HomeExchangePage() {
    val pagerState = rememberPagerState { 2 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray25)
    ) {
        HorizontalDivider(color = Gray100)
        Spacer(modifier = Modifier.height(24.dp))
        OwnedPassView()
        Spacer(modifier = Modifier.height(20.dp))
        ExchangeTabRow(pagerState = pagerState)
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> ExchangeInsightListPage()
                1 -> ExchangeInsightListPage()
            }
        }
    }
}

@Composable
private fun OwnedPassView() {
    val passCount = 2

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
            text = "${passCount}개",
            style = T600_16_22_4,
            color = Orange500
        )
    }
}

@Composable
private fun ExchangeTabRow(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        stringResource(R.string.request_exchange_history),
        stringResource(R.string.requested_exchange_history)
    )

    Column {
        TabRow(
            modifier = Modifier.padding(horizontal = 8.dp),
            selectedTabIndex = pagerState.currentPage,
            containerColor = Gray25,
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(it[pagerState.currentPage])
                        .padding(horizontal = 12.dp),
                    height = 2.dp,
                    color = Gray900
                )
            },
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = pagerState.currentPage == index
                Tab(
                    selected = isSelected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = title,
                            style = T600_16_22_4,
                            color = if (isSelected) Gray900 else Gray500
                        )
                    }
                )
            }
        }
    }
    HorizontalDivider(color = Gray100)
}

@Composable
private fun ExchangeInsightListPage() {
    val exchangeStatus = listOf(
        stringResource(R.string.waiting),
        stringResource(R.string.reject),
        stringResource(R.string.exchange_complete)
    )
    val insights = mutableListOf<String>().apply {
        repeat(33) {
            add("초역세권 대단지 아파트 후기")
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        LazyRow(
            modifier = Modifier.padding(top = 24.dp),
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            itemsIndexed(exchangeStatus) { index, status ->
                CommonChip(
                    text = status,
                    isSelected = index == 0,
                    onClick = {
                        // todo : 교환 상태 선택
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
private fun HomeExchangePagePreview() {
    ImdangTheme {
        HomeExchangePage()
    }
}
