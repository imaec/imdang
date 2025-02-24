package info.imdang.app.ui.insight.write.common

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
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
import info.imdang.component.common.snackbar.showSnackbar
import info.imdang.component.common.topbar.TopBar
import info.imdang.component.system.button.CommonButton
import info.imdang.component.system.textfield.CommonTextField
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R
import kotlinx.coroutines.delay

const val WRITE_INSIGHT_OVERALL_REVIEW_SCREEN = "writeInsightOverallReview"
const val WRITE_INSIGHT_REVIEW_TYPE = "writeInsightReviewType"

enum class WriteInsightReviewType {
    INFRA,
    COMPLEX_ENVIRONMENT,
    COMPLEX_FACILITY,
    GOOD_NEWS;

    companion object {
        fun fromString(type: String): WriteInsightReviewType = entries.first { it.name == type }
    }
}

fun NavGraphBuilder.writeInsightOverallReviewScreen(navController: NavController) {
    composable(
        route = WRITE_INSIGHT_OVERALL_REVIEW_SCREEN +
            "?${WRITE_INSIGHT_REVIEW_TYPE}={${WRITE_INSIGHT_REVIEW_TYPE}}"
    ) {
        val reviewType = it.arguments?.getString(WRITE_INSIGHT_REVIEW_TYPE)?.let { type ->
            WriteInsightReviewType.fromString(type)
        }
        if (reviewType != null) {
            WriteInsightOverallReviewScreen(
                navController = navController,
                viewModel = navController.sharedViewModel(WRITE_INSIGHT_SCREEN),
                reviewType = reviewType
            )
        } else {
            LaunchedEffect(Unit) {
                showSnackbar("$WRITE_INSIGHT_REVIEW_TYPE is null")
                delay(1000)
                navController.popBackStack()
            }
        }
    }
}

@Composable
private fun WriteInsightOverallReviewScreen(
    navController: NavController,
    viewModel: WriteInsightViewModel = hiltViewModel(),
    reviewType: WriteInsightReviewType = WriteInsightReviewType.INFRA
) {
    val focusManager = LocalFocusManager.current
    val originReview = when (reviewType) {
        WriteInsightReviewType.INFRA -> viewModel.infraReview.collectAsStateWithLifecycle().value
        WriteInsightReviewType.COMPLEX_ENVIRONMENT -> {
            viewModel.complexEnvironmentReview.collectAsStateWithLifecycle().value
        }
        WriteInsightReviewType.COMPLEX_FACILITY -> {
            viewModel.complexFacilityReview.collectAsStateWithLifecycle().value
        }
        WriteInsightReviewType.GOOD_NEWS -> {
            viewModel.goodNewsReview.collectAsStateWithLifecycle().value
        }
    }
    var review by remember { mutableStateOf(originReview) }
    var isShowKeyboard by remember { mutableStateOf(false) }
    val title = stringResource(
        when (reviewType) {
            WriteInsightReviewType.INFRA -> R.string.infra_overall_review
            WriteInsightReviewType.COMPLEX_ENVIRONMENT -> {
                R.string.complex_environment_overall_review
            }
            WriteInsightReviewType.COMPLEX_FACILITY -> {
                R.string.complex_facility_overall_review
            }
            WriteInsightReviewType.GOOD_NEWS -> R.string.good_news_overall_review
        }
    )

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
                title = stringResource(
                    when (reviewType) {
                        WriteInsightReviewType.INFRA -> R.string.infra_overall_review
                        WriteInsightReviewType.COMPLEX_ENVIRONMENT -> {
                            R.string.complex_environment_overall_review
                        }
                        WriteInsightReviewType.COMPLEX_FACILITY -> {
                            R.string.complex_facility_overall_review
                        }
                        WriteInsightReviewType.GOOD_NEWS -> R.string.good_news_overall_review
                    }
                ),
                onClickBack = {
                    focusManager.clearFocus()
                    navController.popBackStack()
                }
            )
        },
        content = { contentPadding ->
            WriteInsightOverallReviewContent(
                contentPadding = contentPadding,
                title = title,
                review = review,
                isShowKeyboard = isShowKeyboard,
                onReviewChanged = { review = it }
            )
        },
        bottomBar = {
            CommonButton(
                modifier = Modifier,
                buttonText = stringResource(R.string.confirm),
                isFloating = !isShowKeyboard,
                isEnabled = review.isEmpty() || review.length in 30..200,
                onClick = {
                    focusManager.clearFocus()
                    when (reviewType) {
                        WriteInsightReviewType.INFRA -> viewModel.updateInfraReview(review)
                        WriteInsightReviewType.COMPLEX_ENVIRONMENT -> {
                            viewModel.updateComplexEnvironmentReview(review)
                        }
                        WriteInsightReviewType.COMPLEX_FACILITY -> {
                            viewModel.updateComplexFacilityReview(review)
                        }
                        WriteInsightReviewType.GOOD_NEWS -> viewModel.updateGoodNewsReview(review)
                    }
                    navController.popBackStack()
                }
            )
        }
    )
}

@Composable
private fun WriteInsightOverallReviewContent(
    contentPadding: PaddingValues,
    title: String,
    review: String,
    isShowKeyboard: Boolean,
    onReviewChanged: (String) -> Unit
) {
    val listState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isShowKeyboard) {
        delay(200)
        listState.animateScrollToItem(0)
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

            CommonTextField(
                modifier = Modifier.focusRequester(focusRequester),
                text = review,
                title = title,
                imeAction = ImeAction.Default,
                isSingleLine = false,
                isRequired = true,
                isVisibleValidIcon = false,
                multiLineHeight = 290.dp,
                minLength = 30,
                maxLength = 200,
                onTextChanged = onReviewChanged
            )
        }
    }
}

@Preview
@Composable
private fun WriteInsightOverallReviewScreenPreview() {
    ImdangTheme {
        WriteInsightOverallReviewScreen(navController = rememberNavController())
    }
}
