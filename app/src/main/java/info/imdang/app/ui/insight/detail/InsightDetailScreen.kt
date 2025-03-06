package info.imdang.app.ui.insight.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.model.insight.InsightDetailItem
import info.imdang.app.model.insight.InsightDetailStatus
import info.imdang.app.ui.insight.detail.bottomsheet.ExchangeBottomSheet
import info.imdang.app.ui.insight.detail.preview.FakeInsightDetailViewModel
import info.imdang.component.common.bottomsheet.BottomSheetHandle
import info.imdang.component.common.dialog.CommonDialog
import info.imdang.component.common.image.Icon
import info.imdang.component.common.topbar.CollapsingScaffold
import info.imdang.component.common.topbar.ExitUntilCollapsedScrollBehavior
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.common.topbar.exitUntilCollapsedScrollBehavior
import info.imdang.component.system.button.CommonButton
import info.imdang.component.system.button.CommonButtonType
import info.imdang.component.system.gradient.ButtonGradient
import info.imdang.component.system.tab.ScrollableTabs
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.White
import info.imdang.resource.R
import kotlinx.coroutines.launch

const val INSIGHT_DETAIL_SCREEN = "insightDetail"

fun NavGraphBuilder.insightDetailScreen(navController: NavController) {
    composable(route = "$INSIGHT_DETAIL_SCREEN?insightId={insightId}") {
        InsightDetailScreen(
            navController = navController,
            viewModel = hiltViewModel()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InsightDetailScreen(
    navController: NavController,
    viewModel: InsightDetailViewModel
) {
    val scrollBehavior = exitUntilCollapsedScrollBehavior()

    CollapsingScaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        scrollBehavior = scrollBehavior,
        topBar = {
            InsightDetailTopBar(navController = navController)
        },
        collapsingContent = {
            InsightDetailCollapsingContent(viewModel = viewModel)
        },
        content = {
            InsightDetailContent(
                viewModel = viewModel,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            InsightDetailBottomBar(viewModel = viewModel)
        }
    )
}

@Composable
private fun InsightDetailTopBar(navController: NavController) {
    TopBar(
        hasStatusBarsPadding = false,
        rightWidget = {
            Row {
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .clickable {
                            // todo : 신고
                        }
                        .padding(6.dp),
                    iconResource = R.drawable.ic_report,
                    tint = Gray900
                )
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .clickable {
                            // todo : 공유
                        }
                        .padding(6.dp),
                    iconResource = R.drawable.ic_share,
                    tint = Gray900
                )
            }
        },
        onClickBack = {
            navController.popBackStack()
        }
    )
}

@Composable
private fun InsightDetailContent(
    viewModel: InsightDetailViewModel,
    scrollBehavior: ExitUntilCollapsedScrollBehavior
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var isUserScrolling by remember { mutableStateOf(false) }
    val tabs = listOf(
        stringResource(R.string.basic_info),
        stringResource(R.string.infra),
        stringResource(R.string.complex_environment),
        stringResource(R.string.complex_facility),
        stringResource(R.string.good)
    )
    val insightDetails by viewModel.insightDetails.collectAsStateWithLifecycle()

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect {
                if (!isUserScrolling) {
                    selectedTabIndex = it
                }
            }
    }

    LaunchedEffect(selectedTabIndex) {
        if (isUserScrolling) {
            listState.animateScrollToItem(selectedTabIndex)
            isUserScrolling = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            ScrollableTabs(
                selectedTabIndex = selectedTabIndex,
                tabs = tabs,
                onTabSelected = {
                    if (selectedTabIndex == it) {
                        coroutineScope.launch {
                            listState.animateScrollToItem(selectedTabIndex)
                        }
                    } else {
                        if (it > 0) {
                            coroutineScope.launch {
                                scrollBehavior.collapse()
                            }
                        }
                        isUserScrolling = true
                        selectedTabIndex = it
                    }
                }
            )
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 40.dp)
            ) {
                items(insightDetails) {
                    when (it) {
                        is InsightDetailItem.BasicInfo -> {
                            InsightDetailBasicInfoItems(basicInfo = it)
                        }
                        is InsightDetailItem.Infra -> InsightDetailInfraItems(infra = it.infra)
                        is InsightDetailItem.ComplexEnvironment -> {
                            InsightDetailComplexEnvironmentItems(
                                complexEnvironmentVo = it.complexEnvironment
                            )
                        }
                        is InsightDetailItem.ComplexFacility -> {
                            InsightDetailComplexFacilityItems(
                                complexFacilityVo = it.complexFacility
                            )
                        }
                        is InsightDetailItem.GoodNews -> {
                            InsightDetailGoodNewsItems(goodNewsVo = it.goodNews)
                        }
                        is InsightDetailItem.Invisible -> {
                            InsightDetailInvisibleItem(viewModel = viewModel)
                        }
                    }
                }
            }
        }
        ButtonGradient(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InsightDetailBottomBar(viewModel: InsightDetailViewModel) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isShowExchangeInfoDialog by remember { mutableStateOf(false) }
    var isShowExchangeBottomSheet by remember { mutableStateOf(false) }
    val insightDetailStatus =
        viewModel.insight.collectAsStateWithLifecycle().value.insightDetailStatus

    if (isShowExchangeInfoDialog) {
        CommonDialog(
            message = stringResource(R.string.exchange_info_message),
            onClickPositiveButton = {
                isShowExchangeInfoDialog = false
            },
            onDismiss = {
                isShowExchangeInfoDialog = false
            }
        )
    }

    if (isShowExchangeBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            containerColor = White,
            dragHandle = { BottomSheetHandle() },
            onDismissRequest = { isShowExchangeBottomSheet = false }
        ) {
            ExchangeBottomSheet(
                sheetState = sheetState,
                onCloseBottomSheet = { isShowExchangeBottomSheet = false }
            )
        }
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 40.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (insightDetailStatus == InsightDetailStatus.EXCHANGE_REQUESTED) {
            CommonButton(
                modifier = Modifier.weight(1f),
                horizontalPadding = 0.dp,
                bottomPadding = 0.dp,
                buttonType = CommonButtonType.SUB,
                buttonText = stringResource(R.string.reject),
                onClick = {
                    // todo : 교환 거절
                }
            )
        }
        CommonButton(
            modifier = Modifier.weight(1f),
            horizontalPadding = 0.dp,
            bottomPadding = 0.dp,
            buttonType = when (insightDetailStatus) {
                InsightDetailStatus.EXCHANGE_REQUEST -> CommonButtonType.MAIN
                InsightDetailStatus.EXCHANGE_REQUESTED -> CommonButtonType.MAIN
                InsightDetailStatus.EXCHANGE_WAITING -> CommonButtonType.MAIN
                InsightDetailStatus.EXCHANGE_COMPLETE -> CommonButtonType.MAIN
                InsightDetailStatus.MY_INSIGHT -> CommonButtonType.SUB
            },
            buttonText = when (insightDetailStatus) {
                InsightDetailStatus.EXCHANGE_REQUEST -> stringResource(R.string.request_exchange)
                InsightDetailStatus.EXCHANGE_REQUESTED -> stringResource(R.string.accept)
                InsightDetailStatus.EXCHANGE_WAITING -> stringResource(R.string.waiting)
                InsightDetailStatus.EXCHANGE_COMPLETE -> stringResource(R.string.exchange_complete)
                InsightDetailStatus.MY_INSIGHT -> stringResource(R.string.edit)
            },
            isEnabled = when (insightDetailStatus) {
                InsightDetailStatus.EXCHANGE_REQUEST -> true
                InsightDetailStatus.EXCHANGE_REQUESTED -> true
                InsightDetailStatus.EXCHANGE_WAITING -> false
                InsightDetailStatus.EXCHANGE_COMPLETE -> false
                InsightDetailStatus.MY_INSIGHT -> true
            },
            onClick = {
                when (insightDetailStatus) {
                    InsightDetailStatus.EXCHANGE_REQUEST -> {
                        // todo : 교환 요청
                        isShowExchangeBottomSheet = true
                    }
                    InsightDetailStatus.EXCHANGE_REQUESTED -> {
                        // todo : 교환 수락
                    }
                    InsightDetailStatus.EXCHANGE_WAITING -> {}
                    InsightDetailStatus.EXCHANGE_COMPLETE -> {}
                    InsightDetailStatus.MY_INSIGHT -> {
                        // todo : 인사이트 수정
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun InsightDetailScreenPreview() {
    ImdangTheme {
        InsightDetailScreen(
            navController = rememberNavController(),
            viewModel = FakeInsightDetailViewModel()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExchangeInfoDialogPreview() {
    ImdangTheme {
        CommonDialog(
            message = stringResource(R.string.exchange_info_message)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExchangeRequestCompleteDialogPreview() {
    ImdangTheme {
        CommonDialog(
            message = stringResource(R.string.exchange_request_complete_message),
            subButtonText = stringResource(R.string.check_exchange)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExchangeAcceptDialogPreview() {
    ImdangTheme {
        CommonDialog(
            message = stringResource(R.string.exchange_accept_message),
            subButtonText = stringResource(R.string.check_storage)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExchangeRejectDialogPreview() {
    ImdangTheme {
        CommonDialog(
            message = stringResource(R.string.exchange_reject_message)
        )
    }
}
