package info.imdang.app.ui.insight.write.environment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.imdang.app.ui.insight.write.common.WriteInsightDetailContentView
import info.imdang.app.ui.insight.write.common.WriteInsightSelectionButtons
import info.imdang.component.model.SelectionVo
import info.imdang.component.theme.ImdangTheme
import info.imdang.resource.R

@Composable
fun WriteInsightComplexEnvironmentPage() {
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
                isMultipleSelection = false,
                items = listOf(
                    SelectionVo(name = stringResource(R.string.unknown)),
                    SelectionVo(name = stringResource(R.string.best)),
                    SelectionVo(name = stringResource(R.string.good)),
                    SelectionVo(name = stringResource(R.string.normal)),
                    SelectionVo(name = stringResource(R.string.not_good))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.safety),
                isMultipleSelection = false,
                items = listOf(
                    SelectionVo(name = stringResource(R.string.unknown)),
                    SelectionVo(name = stringResource(R.string.best)),
                    SelectionVo(name = stringResource(R.string.good)),
                    SelectionVo(name = stringResource(R.string.normal)),
                    SelectionVo(name = stringResource(R.string.not_good))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.children_facility),
                isMultipleSelection = false,
                items = listOf(
                    SelectionVo(name = stringResource(R.string.unknown)),
                    SelectionVo(name = stringResource(R.string.best)),
                    SelectionVo(name = stringResource(R.string.good)),
                    SelectionVo(name = stringResource(R.string.normal)),
                    SelectionVo(name = stringResource(R.string.not_good))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.silver_facility),
                isMultipleSelection = false,
                items = listOf(
                    SelectionVo(name = stringResource(R.string.unknown)),
                    SelectionVo(name = stringResource(R.string.best)),
                    SelectionVo(name = stringResource(R.string.good)),
                    SelectionVo(name = stringResource(R.string.normal)),
                    SelectionVo(name = stringResource(R.string.not_good))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightDetailContentView(
                title = stringResource(R.string.infra_overall_review),
                onClick = {
                    // todo : 총평 작성 화면 이동
                }
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 1233)
@Composable
private fun WriteInsightComplexEnvironmentPreview() {
    ImdangTheme {
        WriteInsightComplexEnvironmentPage()
    }
}
