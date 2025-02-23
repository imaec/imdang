package info.imdang.app.ui.insight.write.facility

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
fun WriteInsightComplexFacilityPage() {
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
                title = stringResource(R.string.family),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.not_applicable)),
                    SelectionVo(name = stringResource(R.string.daycare_center)),
                    SelectionVo(name = stringResource(R.string.senior_center))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.multipurpose),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.not_applicable)),
                    SelectionVo(name = stringResource(R.string.multipurpose_room)),
                    SelectionVo(
                        name = stringResource(R.string.resident_representative_conference_room)
                    ),
                    SelectionVo(name = stringResource(R.string.public_laundry)),
                    SelectionVo(name = stringResource(R.string.public_lounge))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.leisure),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.not_applicable)),
                    SelectionVo(name = stringResource(R.string.fitness_center)),
                    SelectionVo(name = stringResource(R.string.reading_room)),
                    SelectionVo(name = stringResource(R.string.sauna)),
                    SelectionVo(name = stringResource(R.string.baths)),
                    SelectionVo(name = stringResource(R.string.screen_golf_course)),
                    SelectionVo(name = stringResource(R.string.cinema)),
                    SelectionVo(name = stringResource(R.string.library)),
                    SelectionVo(name = stringResource(R.string.swimming_pool)),
                    SelectionVo(name = stringResource(R.string.study_room)),
                    SelectionVo(name = stringResource(R.string.daycare_center)),
                    SelectionVo(name = stringResource(R.string.guest_house)),
                    SelectionVo(name = stringResource(R.string.water_park)),
                    SelectionVo(name = stringResource(R.string.breakfast))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.environment),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.not_applicable)),
                    SelectionVo(name = stringResource(R.string.lawn)),
                    SelectionVo(name = stringResource(R.string.sculpture)),
                    SelectionVo(name = stringResource(R.string.bench)),
                    SelectionVo(name = stringResource(R.string.tables_and_chairs)),
                    SelectionVo(name = stringResource(R.string.fountain))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightDetailContentView(
                title = stringResource(R.string.complex_facility_overall_review),
                onClick = {
                    // todo : 총평 작성 화면 이동
                }
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 1415)
@Composable
private fun WriteInsightComplexFacilityPreview() {
    ImdangTheme {
        WriteInsightComplexFacilityPage()
    }
}
