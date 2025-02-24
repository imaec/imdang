package info.imdang.app.ui.insight.write.basic

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.imdang.app.common.ext.sharedViewModel
import info.imdang.app.ui.insight.write.WRITE_INSIGHT_SCREEN
import info.imdang.app.ui.insight.write.WriteInsightViewModel
import info.imdang.app.util.KeyboardCallback
import info.imdang.component.common.image.Icon
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.system.button.CommonButton
import info.imdang.component.system.textfield.CommonTextField
import info.imdang.component.theme.Gray50
import info.imdang.component.theme.Gray600
import info.imdang.component.theme.ImdangTheme
import info.imdang.component.theme.T500_14_19_6
import info.imdang.resource.R
import kotlinx.coroutines.delay

const val WRITE_INSIGHT_SUMMARY_SCREEN = "writeInsightSummary"

fun NavGraphBuilder.writeInsightSummaryScreen(navController: NavController) {
    composable(route = WRITE_INSIGHT_SUMMARY_SCREEN) {
        WriteInsightSummaryScreen(
            navController = navController,
            viewModel = it.sharedViewModel(navController, WRITE_INSIGHT_SCREEN)
        )
    }
}

@Composable
private fun WriteInsightSummaryScreen(
    navController: NavController,
    viewModel: WriteInsightViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val originSummary = viewModel.summary.collectAsStateWithLifecycle().value
    var summary by remember { mutableStateOf(originSummary) }
    var isShowKeyboard by remember { mutableStateOf(false) }

    KeyboardCallback(
        onShowKeyboard = { isShowKeyboard = true },
        onHideKeyboard = { isShowKeyboard = false }
    )

    BackHandler {
        focusManager.clearFocus()
        navController.popBackStack()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            TopBar(
                title = stringResource(R.string.insight_summary),
                onClickBack = {
                    focusManager.clearFocus()
                    navController.popBackStack()
                }
            )
        },
        content = { contentPadding ->
            WriteInsightSummaryContent(
                contentPadding = contentPadding,
                summary = summary,
                isShowKeyboard = isShowKeyboard,
                onSummaryChanged = { summary = it }
            )
        },
        bottomBar = {
            CommonButton(
                modifier = Modifier,
                buttonText = stringResource(R.string.confirm),
                isFloating = !isShowKeyboard,
                isEnabled = summary.length in 30..200,
                onClick = {
                    focusManager.clearFocus()
                    viewModel.updateSummary(summary)
                    navController.popBackStack()
                }
            )
        }
    )
}

@Composable
private fun WriteInsightSummaryContent(
    contentPadding: PaddingValues,
    summary: String,
    isShowKeyboard: Boolean,
    onSummaryChanged: (String) -> Unit
) {
    val listState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isShowKeyboard) {
        delay(200)
        listState.animateScrollToItem(1)
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(),
        contentPadding = PaddingValues(all = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(92.dp)
                    .background(color = Gray50, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .padding(top = 1.dp)
                        .size(16.dp),
                    iconResource = R.drawable.ic_info,
                    tint = Gray600
                )
                Text(
                    text = stringResource(R.string.write_insight_summary_message),
                    style = T500_14_19_6,
                    color = Gray600
                )
            }
        }
        item {
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

            CommonTextField(
                modifier = Modifier.focusRequester(focusRequester),
                text = summary,
                hintText = stringResource(R.string.insight_summary_hint),
                title = stringResource(R.string.insight_summary),
                imeAction = ImeAction.Default,
                isSingleLine = false,
                isRequired = true,
                isVisibleValidIcon = false,
                multiLineHeight = 180.dp,
                minLength = 30,
                maxLength = 200,
                onTextChanged = onSummaryChanged
            )
        }
    }
}

@Preview
@Composable
private fun WriteInsightSummaryScreenPreview() {
    ImdangTheme {
        WriteInsightSummaryScreen(navController = rememberNavController())
    }
}
