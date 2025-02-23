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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import info.imdang.component.theme.Gray25
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
import info.imdang.component.theme.T500_16_22_4
import info.imdang.component.theme.T600_14_19_6
import info.imdang.component.theme.T600_16_22_4
import info.imdang.component.theme.T700_24_33_6
import info.imdang.component.theme.White
import info.imdang.resource.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        StorageAddressView(pagerState = pagerState)
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(thickness = 8.dp, color = Gray50)
    }
}

@Composable
private fun StorageContent() {
    val listState = rememberLazyListState()
    val insights = mutableListOf<String>().apply {
        repeat(33) {
            add("초역세권 대단지 아파트 후기")
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        StorageInsightFilterView(insightCount = insights.size)
        LazyColumn(
            state = listState,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StorageInsightFilterView(insightCount: Int) {
    val sheetState = rememberModalBottomSheetState()
    val viewModel = hiltViewModel<StorageViewModel>()
    val selectedComplex = viewModel.selectedComplex.collectAsStateWithLifecycle().value

    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            containerColor = White,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .width(52.dp)
                        .height(6.dp)
                        .background(
                            color = Color(0xFFD9D9D9),
                            shape = CircleShape
                        )
                )
            },
            onDismissRequest = { showBottomSheet = false }
        ) {
            StorageComplexBottomSheet(
                sheetState = sheetState,
                onCloseBottomSheet = {
                    showBottomSheet = false
                }
            )
        }
    }

    Column {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .background(
                        color = if (selectedComplex == null) Orange500 else White,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = if (selectedComplex == null) Orange500 else Gray100,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .clickable {
                        viewModel.updateSelectedComplex(null)
                    }
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.all),
                    style = T600_14_19_6,
                    color = if (selectedComplex == null) White else Gray500
                )
            }
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .background(
                        color = if (selectedComplex != null) Orange500 else White,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = if (selectedComplex != null) Orange500 else Gray100,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .clickable {
                        showBottomSheet = true
                    }
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedComplex ?: stringResource(R.string.see_by_complex),
                        style = T600_14_19_6,
                        color = if (selectedComplex != null) White else Gray500
                    )
                    Icon(
                        modifier = Modifier.size(12.dp),
                        iconResource = R.drawable.ic_down,
                        tint = if (selectedComplex != null) White else Gray500
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StorageComplexBottomSheet(
    sheetState: SheetState,
    onCloseBottomSheet: () -> Unit
) {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp
    val statusBarHeight = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
    val navigationBarHeight = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<StorageViewModel>()
    val complexes = viewModel.complexes.collectAsStateWithLifecycle().value

    LazyColumn(
        modifier = Modifier.heightIn(
            max = screenHeight - statusBarHeight - navigationBarHeight - 83.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 16.dp,
            bottom = 40.dp
        )
    ) {
        items(complexes) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp)
                    .background(
                        color = Gray25,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        viewModel.updateSelectedComplex(it)
                        coroutineScope.launch {
                            sheetState.hide()
                            delay(100)
                            onCloseBottomSheet()
                        }
                    }
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = it,
                    style = T500_16_22_4,
                    color = Gray900
                )
                Text(
                    text = "${complexes.size}개",
                    style = T500_14_19_6,
                    color = Orange500
                )
            }
        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun StorageComplexBottomSheetPreview() {
    ImdangTheme {
        Box(modifier = Modifier.background(White)) {
            StorageComplexBottomSheet(
                sheetState = rememberModalBottomSheetState(),
                onCloseBottomSheet = {}
            )
        }
    }
}
