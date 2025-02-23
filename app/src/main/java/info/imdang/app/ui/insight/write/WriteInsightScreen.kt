package info.imdang.app.ui.insight.write

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.insight.write.basic.WriteInsightBasicInfoPage
import info.imdang.app.ui.insight.write.environment.WriteInsightComplexEnvironmentPage
import info.imdang.app.ui.insight.write.facility.WriteInsightComplexFacilityPage
import info.imdang.app.ui.insight.write.goodnews.WriteInsightGoodNewsPage
import info.imdang.app.ui.insight.write.infra.WriteInsightInfraPage
import info.imdang.app.util.KeyboardCallback
import info.imdang.component.common.dialog.CommonDialog
import info.imdang.component.common.modifier.isVisible
import info.imdang.component.common.snackbar.showSnackbar
import info.imdang.component.system.button.CommonButton
import info.imdang.component.system.button.CommonButtonType
import info.imdang.component.system.gradient.ButtonGradient
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val WRITE_INSIGHT_SCREEN = "writeInsight"

fun NavGraphBuilder.writeInsightScreen(navController: NavController) {
    composable(route = WRITE_INSIGHT_SCREEN) {
        WriteInsightScreen(navController = navController)
    }
}

@Composable
private fun WriteInsightScreen(navController: NavController) {
    val pagerState = rememberPagerState(initialPage = 0) { 5 }
    var isShowDialog by remember { mutableStateOf(false) }
    var isShowKeyboard by remember { mutableStateOf(false) }

    WriteInsightInit()

    LaunchedEffect(Unit) {
        delay(100)
        isShowDialog = true
    }

    KeyboardCallback(
        onShowKeyboard = { isShowKeyboard = true },
        onHideKeyboard = { isShowKeyboard = false }
    )

    if (isShowDialog) {
        CommonDialog(
            iconResource = R.drawable.ic_sign_for_dialog,
            message = stringResource(R.string.write_insight_message),
            onClickPositiveButton = { isShowDialog = false },
            onDismiss = {}
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            WriteInsightTopBar(navController = navController)
        },
        content = { contentPadding ->
            WriteInsightContent(
                contentPadding = contentPadding,
                pagerState = pagerState,
                isVisibleGradient = !isShowKeyboard
            )
        },
        bottomBar = {
            WriteInsightBottomBar(pagerState = pagerState, isShowKeyboard = isShowKeyboard)
        }
    )
}

@Composable
private fun WriteInsightContent(
    contentPadding: PaddingValues,
    pagerState: PagerState,
    isVisibleGradient: Boolean
) {
    val viewModel = hiltViewModel<WriteInsightViewModel>()

    LaunchedEffect(pagerState.currentPage) {
        viewModel.updateSelectedPage(pagerState.currentPage)
    }

    Box(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> WriteInsightBasicInfoPage()
                1 -> WriteInsightInfraPage()
                2 -> WriteInsightComplexEnvironmentPage()
                3 -> WriteInsightComplexFacilityPage()
                4 -> WriteInsightGoodNewsPage()
            }
        }
        ButtonGradient(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .isVisible(isVisibleGradient)
        )
    }
}

@Composable
private fun WriteInsightBottomBar(pagerState: PagerState, isShowKeyboard: Boolean) {
    val viewModel = hiltViewModel<WriteInsightViewModel>()
    val focusManager = LocalFocusManager.current
    val inputRequiredMessage = stringResource(R.string.write_insight_input_required_message)
    val coroutineScope = rememberCoroutineScope()
    val isButtonEnabled = viewModel.isButtonEnabled.collectAsStateWithLifecycle().value
    val isValidButtonEnabled =
        viewModel.isValidButtonEnabled.collectAsStateWithLifecycle().value
    val animatedHorizontalPadding by animateDpAsState(
        targetValue = if (isShowKeyboard) 0.dp else 20.dp,
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = Modifier.padding(horizontal = animatedHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (pagerState.currentPage in 1..3) {
            CommonButton(
                modifier = Modifier.width(80.dp),
                horizontalPadding = 0.dp,
                buttonType = CommonButtonType.GHOST,
                buttonText = stringResource(R.string.previous),
                onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                }
            )
        }
        CommonButton(
            modifier = Modifier.weight(1f),
            horizontalPadding = 0.dp,
            buttonText = stringResource(
                if (isShowKeyboard) {
                    R.string.confirm
                } else {
                    if (pagerState.currentPage < 4) {
                        R.string.next
                    } else {
                        R.string.write_complete_and_upload
                    }
                }
            ),
            isFloating = !isShowKeyboard,
            isEnabled = if (isShowKeyboard) {
                isValidButtonEnabled
            } else {
                isButtonEnabled
            },
            isClickable = if (isShowKeyboard) {
                isValidButtonEnabled
            } else {
                true
            },
            onClick = {
                if (isShowKeyboard) {
                    focusManager.clearFocus()
                } else {
                    if (isButtonEnabled) {
                        coroutineScope.launch {
                            if (pagerState.currentPage < 4) {
                                viewModel.updateProgress()
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            } else {
                                // todo : 인사이트 작성
                            }
                        }
                    } else {
                        coroutineScope.launch {
                            showSnackbar(message = inputRequiredMessage)
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun WriteInsightInit() {
    val viewModel = hiltViewModel<WriteInsightViewModel>()
    viewModel.initSelectionItems(
        visitTimes = BasicInfoItems.visitTimes(),
        trafficsMethods = BasicInfoItems.traffics(),
        accessLimits = BasicInfoItems.accessLimits(),
        infraTraffics = InfraItems.traffics(),
        schools = InfraItems.schools(),
        livingFacilities = InfraItems.livingFacilities(),
        cultureFacilities = InfraItems.cultureFacilities(),
        surroundingEnvironments = InfraItems.surroundingEnvironments(),
        landmarks = InfraItems.landmarks(),
        avoidFacilities = InfraItems.avoidFacilities(),
        buildings = ComplexEnvironmentItems.buildings(),
        safeties = ComplexEnvironmentItems.safeties(),
        childrenFacilities = ComplexEnvironmentItems.childrenFacilities(),
        silverFacilities = ComplexEnvironmentItems.silverFacilities(),
        familyFacilities = ComplexFacilityItems.familyFacilities(),
        multipurposeFacilities = ComplexFacilityItems.multipurposeFacilities(),
        leisureFacilities = ComplexFacilityItems.leisureFacilities(),
        environments = ComplexFacilityItems.environments(),
        goodNewsTraffics = GoodNewsItems.traffics(),
        developments = GoodNewsItems.developments(),
        educations = GoodNewsItems.educations(),
        naturalEnvironments = GoodNewsItems.naturalEnvironments(),
        cultures = GoodNewsItems.cultures(),
        industries = GoodNewsItems.industries(),
        policies = GoodNewsItems.policies()
    )
}

@Preview
@Composable
private fun WriteInsightScreenPreview() {
    ImdangTheme {
        WriteInsightScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteInsightDialogPreview() {
    ImdangTheme {
        CommonDialog(
            iconResource = R.drawable.ic_sign_for_dialog,
            message = stringResource(R.string.write_insight_message),
            onDismiss = {}
        )
    }
}
