package info.imdang.app.ui.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.main.home.exchange.HomeExchangePage
import info.imdang.app.ui.main.home.preview.FakeHomeViewModel
import info.imdang.app.ui.main.home.search.HomeSearchPage
import info.imdang.app.ui.my.MY_SCREEN
import info.imdang.app.ui.notification.NOTIFICATION_SCREEN
import info.imdang.app.ui.onboarding.ONBOARDING_SCREEN
import info.imdang.component.common.image.Icon
import info.imdang.component.common.modifier.clickableWithoutRipple
import info.imdang.component.common.shape.TooltipDirection
import info.imdang.component.common.tooltip.Tooltip
import info.imdang.component.system.button.CommonButton
import info.imdang.component.system.button.CommonButtonType
import info.imdang.component.theme.Gray500
import info.imdang.component.theme.Gray700
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.Orange50
import info.imdang.component.theme.Orange500
import info.imdang.component.theme.T500_14_19_6
import info.imdang.component.theme.T500_16_22_4
import info.imdang.component.theme.T700_24_33_6
import info.imdang.component.theme.White
import info.imdang.resource.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val HOME_SCREEN = "home"

fun NavGraphBuilder.homeScreen(
    navController: NavController,
    mainNavController: NavController
) {
    composable(route = HOME_SCREEN) {
        HomeScreen(
            navController = navController,
            mainNavController = mainNavController,
            viewModel = hiltViewModel()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    navController: NavController,
    mainNavController: NavController,
    viewModel: HomeViewModel
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val pagerState = rememberPagerState { 2 }
    var isShowBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.hideTooltip()
    }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest {
            when (it) {
                is HomeEvent.ShowHomeFreePassBottomSheet -> {
                    isShowBottomSheet = true
                }
            }
        }
    }

    if (isShowBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            containerColor = White,
            dragHandle = null,
            onDismissRequest = { isShowBottomSheet = false }
        ) {
            FreePassBottomSheet(
                viewModel = viewModel,
                sheetState = sheetState,
                onCloseBottomSheet = { isBack ->
                    isShowBottomSheet = false
                    if (isBack) {
                        navController.popBackStack(route = ONBOARDING_SCREEN, inclusive = true)
                    }
                }
            )
        }
    }

    Box {
        Scaffold(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize(),
            topBar = {
                HomeTabBar(
                    navController = navController,
                    viewModel = viewModel,
                    pagerState = pagerState
                )
            },
            content = { contentPadding ->
                HomeContent(
                    navController = navController,
                    viewModel = viewModel,
                    pagerState = pagerState,
                    contentPadding = contentPadding
                )
            }
        )
        if (viewModel.isShowTooltip.collectAsStateWithLifecycle().value) {
            Tooltip(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 20.dp, top = 52.dp),
                direction = TooltipDirection.UP,
                tailStartPadding = 80.dp,
                text = stringResource(R.string.free_pass_tooltip_message)
            )
        }
    }
}

@Composable
private fun HomeTabBar(
    navController: NavController,
    viewModel: HomeViewModel,
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()
    val hasNewNotification by viewModel.hasNewNotification.collectAsStateWithLifecycle()
    val tabs = listOf(
        stringResource(R.string.home_search),
        stringResource(R.string.home_exchange)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 8.dp, bottom = 16.dp)
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            tabs.forEachIndexed { index, title ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickableWithoutRipple {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = title,
                        style = T700_24_33_6,
                        color = if (isSelected) Gray900 else Gray500
                    )
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate(route = NOTIFICATION_SCREEN)
                    }
                    .padding(8.dp)
                    .size(24.dp),
                iconResource = if (hasNewNotification) {
                    R.drawable.ic_alarm_new
                } else {
                    R.drawable.ic_alarm
                },
                tint = if (hasNewNotification) Color.Unspecified else Gray900
            )
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate(route = MY_SCREEN)
                    },
                iconResource = R.drawable.ic_profile
            )
        }
    }
}

@Composable
private fun HomeContent(
    navController: NavController,
    viewModel: HomeViewModel,
    contentPadding: PaddingValues,
    pagerState: PagerState
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) { page ->
        when (page) {
            0 -> HomeSearchPage(
                navController = navController,
                viewModel = viewModel
            )
            1 -> HomeExchangePage(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FreePassBottomSheet(
    viewModel: HomeViewModel,
    sheetState: SheetState,
    onCloseBottomSheet: (isBack: Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(top = 16.dp, bottom = 40.dp)) {
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .clickable {
                        coroutineScope.launch {
                            sheetState.hide()
                            delay(100)
                            onCloseBottomSheet(false)
                        }
                    }
                    .padding(8.dp)
                    .size(20.dp),
                iconResource = R.drawable.ic_close,
                tint = Gray900
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(100.dp),
                iconResource = R.drawable.ic_free_pass
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.free_pass_title),
                    style = T700_24_33_6,
                    color = Gray900
                )
                Text(
                    text = stringResource(R.string.free_pass_sub_title),
                    style = T500_16_22_4,
                    color = Gray700,
                    textAlign = TextAlign.Center
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .background(
                        color = Orange50,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.free_pass_description, "길동"),
                    style = T500_14_19_6,
                    color = Orange500,
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CommonButton(
                    modifier = Modifier.weight(1f),
                    buttonType = CommonButtonType.GHOST,
                    bottomPadding = 0.dp,
                    horizontalPadding = 0.dp,
                    buttonText = stringResource(R.string.not_show_today),
                    onClick = {
                        viewModel.setCloseTimeOfHomeFreePass()
                        coroutineScope.launch {
                            sheetState.hide()
                            delay(100)
                            onCloseBottomSheet(false)
                        }
                    }
                )
                CommonButton(
                    modifier = Modifier.weight(1f),
                    bottomPadding = 0.dp,
                    horizontalPadding = 0.dp,
                    buttonText = stringResource(R.string.agree_and_receive),
                    onClick = {
                        viewModel.showTooltip()
                        coroutineScope.launch {
                            sheetState.hide()
                            delay(100)
                            onCloseBottomSheet(false)
                        }
                    }
                )
            }
        }
    }
}

@Preview(heightDp = 1620)
@Composable
private fun HomeScreenPreview() {
    ImdangTheme {
        HomeScreen(
            navController = rememberNavController(),
            mainNavController = rememberNavController(),
            viewModel = FakeHomeViewModel()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun FreePassBottomSheetPreview() {
    ImdangTheme {
        FreePassBottomSheet(
            viewModel = FakeHomeViewModel(),
            sheetState = rememberModalBottomSheetState(),
            onCloseBottomSheet = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FreePassTooltipPreview() {
    ImdangTheme {
        Tooltip(
            modifier = Modifier.padding(start = 20.dp, top = 52.dp),
            tailStartPadding = 80.dp,
            direction = TooltipDirection.UP,
            text = stringResource(R.string.free_pass_tooltip_message)
        )
    }
}
