package info.imdang.app.ui.insight.write.environment

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import info.imdang.app.ui.insight.write.WriteInsightViewModel
import info.imdang.app.ui.insight.write.common.WriteInsightDetailContentView
import info.imdang.app.ui.insight.write.common.WriteInsightSelectionButtons
import info.imdang.app.ui.insight.write.WriteInsightInit
import info.imdang.app.ui.insight.write.common.WRITE_INSIGHT_OVERALL_REVIEW_SCREEN
import info.imdang.app.ui.insight.write.common.WRITE_INSIGHT_REVIEW_TYPE
import info.imdang.app.ui.insight.write.common.WriteInsightReviewType
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R
import kotlinx.coroutines.launch

@Composable
fun WriteInsightComplexEnvironmentPage(navController: NavController = rememberNavController()) {
    val viewModel = hiltViewModel<WriteInsightViewModel>()
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
                title = stringResource(R.string.building),
                selectionItems = viewModel.buildings,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.safety),
                selectionItems = viewModel.safeties,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(1)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.children_facility),
                selectionItems = viewModel.childrenFacilities,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(2)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.silver_facility),
                selectionItems = viewModel.silverFacilities,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(3)
                    }
                }
            )
        }
        item {
            WriteInsightDetailContentView(
                title = stringResource(R.string.complex_environment_overall_review),
                text = viewModel.complexEnvironmentReview.collectAsStateWithLifecycle().value,
                onClick = {
                    navController.navigate(
                        route = WRITE_INSIGHT_OVERALL_REVIEW_SCREEN +
                            "?$WRITE_INSIGHT_REVIEW_TYPE=" +
                            WriteInsightReviewType.COMPLEX_ENVIRONMENT.name
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 1233)
@Composable
private fun WriteInsightComplexEnvironmentPreview() {
    WriteInsightInit()
    ImdangTheme {
        WriteInsightComplexEnvironmentPage()
    }
}
