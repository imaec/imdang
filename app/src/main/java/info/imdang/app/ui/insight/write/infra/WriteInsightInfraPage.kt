package info.imdang.app.ui.insight.write.infra

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import info.imdang.app.ui.insight.write.InfraItems
import info.imdang.app.ui.insight.write.WriteInsightInit
import info.imdang.app.ui.insight.write.WriteInsightSelectionItems
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
fun WriteInsightInfraPage(
    navController: NavController,
    viewModel: WriteInsightViewModel
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
                selectionItems = viewModel.infraTraffics,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.school),
                selectionItems = viewModel.schools,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(1)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.living_facility),
                selectionItems = viewModel.livingFacilities,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(2)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.culture_facility),
                selectionItems = viewModel.cultureFacilities,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(3)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.surrounding_environment),
                selectionItems = viewModel.surroundingEnvironments,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(4)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.landmark),
                selectionItems = viewModel.landmarks,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(5)
                    }
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.avoid_facility),
                selectionItems = viewModel.avoidFacilities,
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(6)
                    }
                }
            )
        }
        item {
            WriteInsightDetailContentView(
                title = stringResource(R.string.infra_overall_review),
                text = viewModel.infraReview.collectAsStateWithLifecycle().value,
                onClick = {
                    navController.navigate(
                        route = WRITE_INSIGHT_OVERALL_REVIEW_SCREEN +
                            "?${WRITE_INSIGHT_REVIEW_TYPE}=${WriteInsightReviewType.INFRA.name}"
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 1950)
@Composable
private fun WriteInsightInfraPagePreview1() {
    val viewModel = FakeWriteInsightViewModel()
    WriteInsightInit(viewModel = viewModel)
    ImdangTheme {
        WriteInsightInfraPage(
            navController = rememberNavController(),
            viewModel = viewModel
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteInsightInfraPagePreview2() {
    WriteInsightInit(viewModel = FakeWriteInsightViewModel())
    ImdangTheme {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.avoid_facility),
                selectionItems = WriteInsightSelectionItems().apply {
                    setItems(InfraItems.avoidFacilities())
                }
            )
            WriteInsightDetailContentView(
                title = stringResource(R.string.complex_environment_overall_review),
                text = "",
                onClick = {}
            )
        }
    }
}
