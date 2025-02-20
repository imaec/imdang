package info.imdang.app.ui.main.storage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.insight.InsightItem
import info.imdang.app.ui.insight.InsightItemType
import info.imdang.app.ui.main.storage.address.STORAGE_ADDRESS_SCREEN
import info.imdang.app.ui.main.storage.map.STORAGE_BY_MAP_SCREEN
import info.imdang.component.common.image.Icon
import info.imdang.component.common.modifier.clickableWithoutRipple
import info.imdang.component.common.modifier.isVisible
import info.imdang.component.common.topbar.CollapsingScaffold
import info.imdang.component.common.topbar.exitUntilCollapsedScrollBehavior
import info.imdang.component.theme.Gray100
import info.imdang.component.theme.Gray200
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T400_14_19_6
import info.imdang.component.theme.T500_12_16_8
import info.imdang.component.theme.T500_14_19_6
import info.imdang.component.theme.T600_14_19_6
import info.imdang.component.theme.T600_16_22_4
import info.imdang.component.theme.T600_18_25_2
import info.imdang.component.theme.T700_24_33_6
import info.imdang.component.theme.White
import info.imdang.resource.R

const val STORAGE_SCREEN = "storage"

fun NavGraphBuilder.storageScreen(
    navController: NavController,
    mainNavController: NavController
) {
    composable(route = STORAGE_SCREEN) {
        StorageScreen(
            navController = navController,
            mainNavController = mainNavController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StorageScreen(
    navController: NavController,
    mainNavController: NavController
) {
    val scrollBehavior = exitUntilCollapsedScrollBehavior()
    var isCollapsed by remember { mutableStateOf(false) }

    CollapsingScaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        scrollBehavior = scrollBehavior,
        onCollapseChanged = {
            isCollapsed = it
        },
        topBar = {
            StorageTopBar(
                navController = navController,
                isCollapsed = isCollapsed
            )
        },
        collapsingContent = {
            StorageCollapsingContent(navController = navController)
        },
        content = {
            StorageContent()
        }
    )
}

@Composable
private fun StorageTopBar(
    navController: NavController,
    isCollapsed: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 8.dp, bottom = 16.dp)
                .fillMaxWidth()
                .height(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.clickable(enabled = isCollapsed) {
                    navController.navigate(STORAGE_ADDRESS_SCREEN)
                },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isCollapsed) "신논현동" else stringResource(R.string.storage),
                    style = T700_24_33_6,
                    color = Gray900
                )
                Icon(
                    modifier = Modifier
                        .isVisible(isCollapsed)
                        .size(20.dp),
                    iconResource = R.drawable.ic_down,
                    tint = Gray900
                )
            }
            Row(
                modifier = Modifier
                    .background(
                        color = White,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Gray200,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clip(shape = RoundedCornerShape(4.dp))
                    .clickable {
                        navController.navigate(STORAGE_BY_MAP_SCREEN)
                    }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    iconResource = R.drawable.ic_map,
                    tint = Gray700
                )
                Text(
                    text = stringResource(R.string.map),
                    style = T500_12_16_8,
                    color = Gray700
                )
            }
        }
        HorizontalDivider(color = Gray100)
    }
}

@Composable
private fun StorageCollapsingContent(navController: NavController) {
    val pagerState = rememberPagerState { 4 }

    Column {
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(28.dp)
                    .background(
                        color = Orange50,
                        shape = CircleShape
                    )
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "${pagerState.currentPage + 1} / ${pagerState.pageCount}",
                    style = T600_14_19_6,
                    color = Orange500
                )
            }
            Text(
                modifier = Modifier.clickableWithoutRipple {
                    navController.navigate(STORAGE_ADDRESS_SCREEN)
                },
                text = stringResource(R.string.see_all),
                style = T400_14_19_6,
                color = Gray700
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 20.dp),
            pageSpacing = 12.dp
        ) { page ->
            StorageAddressPage(
                addressCount = pagerState.pageCount,
                isSelected = pagerState.currentPage == page
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(thickness = 8.dp, color = Gray50)
    }
}

@Composable
private fun StorageAddressPage(
    isSelected: Boolean,
    addressCount: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) Orange500 else Gray100,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(all = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                iconResource = R.drawable.ic_pin_gray,
                tint = White
            )
            Text(
                text = "서울시 강남구 신논현동",
                style = T600_18_25_2,
                color = White
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
                    .background(
                        color = White.copy(alpha = 0.16f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.complex),
                    style = T500_14_19_6,
                    color = White
                )
                Text(
                    text = "${addressCount}개",
                    style = T600_16_22_4,
                    color = White
                )
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
                    .background(
                        color = White.copy(alpha = 0.16f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.insight),
                    style = T500_14_19_6,
                    color = White
                )
                Text(
                    text = "3개",
                    style = T600_16_22_4,
                    color = White
                )
            }
        }
    }
}

@Composable
private fun StorageContent() {
    val state = rememberLazyListState()
    val insights = mutableListOf<String>().apply {
        repeat(33) {
            add("초역세권 대단지 아파트 후기")
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        StorageInsightFilterView(insightCount = insights.size)
        LazyColumn(
            state = state,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
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

@Composable
private fun StorageInsightFilterView(insightCount: Int) {
    Column {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .background(
                        color = Orange500,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .clickable {
                        // todo : 주소별 인사이트 조회
                    }
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.all),
                    style = T600_14_19_6,
                    color = White
                )
            }
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .background(
                        color = White,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = Gray100,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .clickable {
                        // todo : 주소별 단지 목록 바텀시트 노출
                    }
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.see_by_complex),
                        style = T600_14_19_6,
                        color = Gray500
                    )
                    Icon(
                        modifier = Modifier.size(12.dp),
                        iconResource = R.drawable.ic_down,
                        tint = Gray500
                    )
                }
            }
        }
        HorizontalDivider(color = Gray100)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .height(22.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.see_just_my_insight),
                    style = T500_14_19_6,
                    color = Gray700
                )
                Icon(
                    modifier = Modifier
                        .width(32.dp)
                        .height(18.dp)
                        .clip(CircleShape)
                        .clickable {
                            // todo : 내 인사이트만 보기 on/off
                        },
                    iconResource = R.drawable.ic_switch_off
                )
            }
            Text(
                text = "${insightCount}개",
                style = T600_16_22_4,
                color = Gray900
            )
        }
        HorizontalDivider(color = Gray100)
    }
}

@Preview
@Composable
private fun StorageScreenPreview() {
    ImdangTheme {
        StorageScreen(
            navController = rememberNavController(),
            mainNavController = rememberNavController()
        )
    }
}
