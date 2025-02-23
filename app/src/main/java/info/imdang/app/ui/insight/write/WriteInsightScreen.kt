package info.imdang.app.ui.insight.write

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
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
import androidx.hilt.navigation.compose.hiltViewModel
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
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    var isShowDialog by remember { mutableStateOf(false) }
    var isShowKeyboard by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        isShowDialog = true
    }

    if (isShowDialog) {
        CommonDialog(
            iconResource = R.drawable.ic_sign_for_dialog,
            message = stringResource(R.string.write_insight_message),
            onClickPositiveButton = { isShowDialog = false },
            onDismiss = {}
        )
    }

    KeyboardCallback(
        onShowKeyboard = { isShowKeyboard = true },
        onHideKeyboard = { isShowKeyboard = false }
    )

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
                isShowKeyboard = isShowKeyboard
            )
        },
        bottomBar = {
            val message = stringResource(R.string.write_insight_input_required_message)
            CommonButton(
                buttonText = stringResource(
                    if (isShowKeyboard) R.string.confirm else R.string.next
                ),
                isFloating = !isShowKeyboard,
                isEnabled = isShowKeyboard,
                isClickable = true,
                onClick = {
                    focusManager.clearFocus()
                    if (!isShowKeyboard) {
                        coroutineScope.launch {
                            showSnackbar(message = message)
                        }
                    }
                }
            )
        }
    )
}

@Composable
private fun WriteInsightContent(
    contentPadding: PaddingValues,
    isShowKeyboard: Boolean
) {
    val viewModel = hiltViewModel<WriteInsightViewModel>()
    val pagerState = rememberPagerState { 5 }

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
                .isVisible(!isShowKeyboard)
        )
    }
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
