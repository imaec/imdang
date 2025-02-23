package info.imdang.app.ui.insight.write.goodnews

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
fun WriteInsightGoodNewsPage() {
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
                items = listOf(
                    SelectionVo(name = stringResource(R.string.unknown)),
                    SelectionVo(name = stringResource(R.string.subway_opens)),
                    SelectionVo(
                        name = stringResource(R.string.new_high_speed_rail_station_established)
                    ),
                    SelectionVo(name = stringResource(R.string.transportation_hub_designation))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.development),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.unknown)),
                    SelectionVo(name = stringResource(R.string.redevelopment)),
                    SelectionVo(name = stringResource(R.string.reconstruction)),
                    SelectionVo(name = stringResource(R.string.remodeling)),
                    SelectionVo(name = stringResource(R.string.nearby_new_town_development)),
                    SelectionVo(name = stringResource(R.string.complex_development)),
                    SelectionVo(name = stringResource(R.string.large_shopping_mall)),
                    SelectionVo(name = stringResource(R.string.department_store)),
                    SelectionVo(name = stringResource(R.string.large_office_complex))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.education),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.unknown)),
                    SelectionVo(name = stringResource(R.string.new_elementary_school)),
                    SelectionVo(name = stringResource(R.string.new_high_school)),
                    SelectionVo(name = stringResource(R.string.special_high_school)),
                    SelectionVo(name = stringResource(R.string.private_high_school)),
                    SelectionVo(name = stringResource(R.string.international_school)),
                    SelectionVo(name = stringResource(R.string.college_campus))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.natural_environment),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.unknown)),
                    SelectionVo(name = stringResource(R.string.large_park)),
                    SelectionVo(name = stringResource(R.string.river_restoration)),
                    SelectionVo(name = stringResource(R.string.lake_restoration))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.culture),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.unknown)),
                    SelectionVo(name = stringResource(R.string.large_hospital)),
                    SelectionVo(name = stringResource(R.string.cultural_center)),
                    SelectionVo(name = stringResource(R.string.library)),
                    SelectionVo(name = stringResource(R.string.performance_hall)),
                    SelectionVo(name = stringResource(R.string.gym))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.industry),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.unknown)),
                    SelectionVo(name = stringResource(R.string.industry_complex)),
                    SelectionVo(name = stringResource(R.string.corporate_relocation)),
                    SelectionVo(name = stringResource(R.string.startup_complex))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.policy),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.unknown)),
                    SelectionVo(
                        name = stringResource(R.string.speculative_overheating_district_unlocked)
                    ),
                    SelectionVo(name = stringResource(R.string.deregulation)),
                    SelectionVo(name = stringResource(R.string.special_zone_designation)),
                    SelectionVo(name = stringResource(R.string.job_creation_policy))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightDetailContentView(
                title = stringResource(R.string.good_news_overall_review),
                onClick = {
                    // todo : 총평 작성 화면 이동
                }
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 2000)
@Composable
private fun WriteInsightGoodNewsPagePreview() {
    ImdangTheme {
        WriteInsightGoodNewsPage()
    }
}
