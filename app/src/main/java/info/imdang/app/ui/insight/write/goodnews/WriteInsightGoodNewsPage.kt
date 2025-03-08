package info.imdang.app.ui.insight.write.goodnews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.insight.write.WriteInsightInit
import info.imdang.app.ui.insight.write.WriteInsightViewModel
import info.imdang.app.ui.insight.write.common.WRITE_INSIGHT_OVERALL_REVIEW_SCREEN
import info.imdang.app.ui.insight.write.common.WRITE_INSIGHT_REVIEW_TYPE
import info.imdang.app.ui.insight.write.common.WriteInsightDetailContentView
import info.imdang.app.ui.insight.write.common.WriteInsightReviewType
import info.imdang.app.ui.insight.write.common.WriteInsightSelectionButtons
import info.imdang.app.ui.insight.write.preview.FakeWriteInsightViewModel
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R
import kotlinx.coroutines.launch

@Composable
fun WriteInsightGoodNewsPage(
    navController: NavController,
    viewModel: WriteInsightViewModel,
    onHideTooltip: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 24.dp,
            bottom = 40.dp
        ),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.traffic),
                selectionItems = viewModel.goodNewsTraffics,
                onClick = {
                    onHideTooltip()
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.development),
                selectionItems = viewModel.developments,
                onClick = {
                    onHideTooltip()
                    coroutineScope.launch {
                        listState.animateScrollToItem(1)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.education),
                selectionItems = viewModel.educations,
                onClick = {
                    onHideTooltip()
                    coroutineScope.launch {
                        listState.animateScrollToItem(2)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.natural_environment),
                selectionItems = viewModel.naturalEnvironments,
                onClick = {
                    onHideTooltip()
                    coroutineScope.launch {
                        listState.animateScrollToItem(3)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.culture),
                selectionItems = viewModel.cultures,
                onClick = {
                    onHideTooltip()
                    coroutineScope.launch {
                        listState.animateScrollToItem(4)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.industry),
                selectionItems = viewModel.industries,
                onClick = {
                    onHideTooltip()
                    coroutineScope.launch {
                        listState.animateScrollToItem(5)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.policy),
                selectionItems = viewModel.policies,
                onClick = {
                    onHideTooltip()
                    coroutineScope.launch {
                        listState.animateScrollToItem(6)
                    }
                }
            )
        }
        item {
            WriteInsightDetailContentView(
                title = stringResource(R.string.good_news_overall_review),
                text = viewModel.goodNewsReview.collectAsStateWithLifecycle().value,
                onClick = {
                    onHideTooltip()
                    navController.navigate(
                        route = WRITE_INSIGHT_OVERALL_REVIEW_SCREEN +
                            "?$WRITE_INSIGHT_REVIEW_TYPE=${WriteInsightReviewType.GOOD_NEWS.name}"
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 2000)
@Composable
private fun WriteInsightGoodNewsPagePreview() {
    val viewModel = FakeWriteInsightViewModel()
    WriteInsightInit(viewModel = viewModel)
    ImdangTheme {
        WriteInsightGoodNewsPage(
            navController = rememberNavController(),
            viewModel = viewModel,
            onHideTooltip = {}
        )
    }
}
