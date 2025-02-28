package info.imdang.app.ui.insight.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.insight.write.ComplexEnvironmentItems
import info.imdang.app.ui.insight.write.ComplexFacilityItems
import info.imdang.app.ui.insight.write.GoodNewsItems
import info.imdang.app.ui.insight.write.InfraItems
import info.imdang.component.common.dialog.CommonDialog
import info.imdang.component.common.image.Icon
import info.imdang.component.common.topbar.CollapsingScaffold
import info.imdang.component.common.topbar.ExitUntilCollapsedScrollBehavior
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.common.topbar.exitUntilCollapsedScrollBehavior
import info.imdang.component.system.button.CommonButton
import info.imdang.component.system.gradient.ButtonGradient
import info.imdang.component.system.tab.ScrollableTabs
import info.imdang.component.theme.Gray900
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R
import kotlinx.coroutines.launch

const val INSIGHT_DETAIL_SCREEN = "insightDetail"

fun NavGraphBuilder.insightDetailScreen(navController: NavController) {
    composable(route = INSIGHT_DETAIL_SCREEN) {
        InsightDetailScreen(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InsightDetailScreen(navController: NavController) {
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
            InsightDetailCollapsingContent()
        },
        content = {
            InsightDetailContent(scrollBehavior = scrollBehavior)
        },
        bottomBar = {
            InsightDetailBottomBar()
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
private fun InsightDetailContent(scrollBehavior: ExitUntilCollapsedScrollBehavior) {
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
                item {
                    InsightDetailBasicInfoItems(
                        address = "서울특별시 영등포구 당산 2동 123-467",
                        complexName = "당산아이파크1차",
                        visitDate = "2024.01.01",
                        visitTimes = listOf("아침", "저녁"),
                        trafficMethods = listOf("자차", "도보"),
                        accessLimit = "자유로움",
                        summary = "단지는 전반적으로 관리 상태가 양호했으며, 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. 단지는 전반적으로 관리 상태가 양호했으며," +
                            " 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. 단지는 전반적으로 관리 상태가 양호했으며, " +
                            "주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요."
                    )
                }
                item {
                    InsightDetailInfraItems(
                        traffics = InfraItems.traffics().map { it.name },
                        schools = InfraItems.schools().map { it.name },
                        livingFacilities = InfraItems.livingFacilities().map { it.name },
                        cultureFacilities = InfraItems.cultureFacilities().map { it.name },
                        surroundingEnvironments = InfraItems.surroundingEnvironments()
                            .map { it.name },
                        landmarks = InfraItems.landmarks().map { it.name },
                        avoidFacilities = InfraItems.avoidFacilities().map { it.name },
                        infraReview = "단지는 전반적으로 관리 상태가 양호했으며, 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. 단지는 전반적으로 관리 상태가 양호했으며," +
                            " 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. 단지는 전반적으로 관리 상태가 양호했으며, " +
                            "주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요."
                    )
                }
                item {
                    InsightDetailComplexEnvironmentItems(
                        building = ComplexEnvironmentItems.buildings().first().name,
                        safety = ComplexEnvironmentItems.safeties().first().name,
                        childrenFacility = ComplexEnvironmentItems.childrenFacilities()
                            .first().name,
                        silverFacility = ComplexEnvironmentItems.silverFacilities().first().name,
                        complexEnvironmentReview = "단지는 전반적으로 관리 상태가 양호했으며, 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. 단지는 전반적으로 관리 상태가 양호했으며," +
                            " 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. 단지는 전반적으로 관리 상태가 양호했으며, " +
                            "주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요."
                    )
                }
                item {
                    InsightDetailComplexFacilityItems(
                        familyFacilities = ComplexFacilityItems.familyFacilities().map { it.name },
                        multipurposeFacilities = ComplexFacilityItems.multipurposeFacilities()
                            .map { it.name },
                        leisureFacilities = ComplexFacilityItems.leisureFacilities()
                            .map { it.name },
                        environments = ComplexFacilityItems.environments().map { it.name },
                        complexFacilityReview = "단지는 전반적으로 관리 상태가 양호했으며, 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. 단지는 전반적으로 관리 상태가 양호했으며," +
                            " 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. 단지는 전반적으로 관리 상태가 양호했으며, " +
                            "주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요."
                    )
                }
                item {
                    InsightDetailGoodNewsItems(
                        traffics = GoodNewsItems.traffics().map { it.name },
                        developments = GoodNewsItems.developments().map { it.name },
                        educations = GoodNewsItems.educations().map { it.name },
                        naturalEnvironments = GoodNewsItems.naturalEnvironments().map { it.name },
                        cultures = GoodNewsItems.cultures().map { it.name },
                        industries = GoodNewsItems.industries().map { it.name },
                        policies = GoodNewsItems.policies().map { it.name },
                        goodNewsReview = "단지는 전반적으로 관리 상태가 양호했으며, 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. 단지는 전반적으로 관리 상태가 양호했으며," +
                            " 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. 단지는 전반적으로 관리 상태가 양호했으며, " +
                            "주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. 다만 주차 공간이 협소하고, " +
                            "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요."
                    )
                }
            }
        }
        ButtonGradient(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
private fun InsightDetailBottomBar() {
    var isShowExchangeInfoDialog by remember { mutableStateOf(false) }

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

    CommonButton(
        buttonText = stringResource(R.string.request_exchange),
        onClick = {
            // todo : 교환 요청
            isShowExchangeInfoDialog = true
        }
    )
}

@Preview
@Composable
private fun InsightDetailScreenPreview() {
    ImdangTheme {
        InsightDetailScreen(navController = rememberNavController())
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
