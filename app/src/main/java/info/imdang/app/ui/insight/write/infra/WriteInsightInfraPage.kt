package info.imdang.app.ui.insight.write.infra

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
fun WriteInsightInfraPage() {
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
                    SelectionVo(name = stringResource(R.string.not_applicable)),
                    SelectionVo(name = stringResource(R.string.parking_convenient)),
                    SelectionVo(name = stringResource(R.string.around_subway_station)),
                    SelectionVo(name = stringResource(R.string.around_the_bus_stop))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.school),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.not_applicable)),
                    SelectionVo(name = stringResource(R.string.apartment_with_elementary_school)),
                    SelectionVo(name = stringResource(R.string.daycare_center)),
                    SelectionVo(name = stringResource(R.string.middle_school)),
                    SelectionVo(name = stringResource(R.string.high_school)),
                    SelectionVo(name = stringResource(R.string.academy_district))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.living_facility),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.not_applicable)),
                    SelectionVo(name = stringResource(R.string.community_center)),
                    SelectionVo(name = stringResource(R.string.convenience_store)),
                    SelectionVo(name = stringResource(R.string.small_mart)),
                    SelectionVo(name = stringResource(R.string.large_mart)),
                    SelectionVo(name = stringResource(R.string.hospital)),
                    SelectionVo(name = stringResource(R.string.bank)),
                    SelectionVo(name = stringResource(R.string.cafe)),
                    SelectionVo(name = stringResource(R.string.beauty_salon)),
                    SelectionVo(name = stringResource(R.string.pharmacy)),
                    SelectionVo(name = stringResource(R.string.post_office))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.culture_facility),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.not_applicable)),
                    SelectionVo(name = stringResource(R.string.library)),
                    SelectionVo(name = stringResource(R.string.cinema)),
                    SelectionVo(name = stringResource(R.string.gym)),
                    SelectionVo(name = stringResource(R.string.health_center)),
                    SelectionVo(name = stringResource(R.string.swimming_pool)),
                    SelectionVo(name = stringResource(R.string.badminton_court)),
                    SelectionVo(name = stringResource(R.string.tennis_court)),
                    SelectionVo(name = stringResource(R.string.golf_driving_range))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.surrounding_environment),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.not_applicable)),
                    SelectionVo(name = stringResource(R.string.river)),
                    SelectionVo(name = stringResource(R.string.ocean)),
                    SelectionVo(name = stringResource(R.string.mountain)),
                    SelectionVo(name = stringResource(R.string.park)),
                    SelectionVo(name = stringResource(R.string.walking_trail)),
                    SelectionVo(name = stringResource(R.string.church)),
                    SelectionVo(name = stringResource(R.string.cathedral)),
                    SelectionVo(name = stringResource(R.string.restaurant_area)),
                    SelectionVo(name = stringResource(R.string.market))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.landmark),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.not_applicable)),
                    SelectionVo(name = stringResource(R.string.amusement_park)),
                    SelectionVo(name = stringResource(R.string.complex_shopping_mall)),
                    SelectionVo(name = stringResource(R.string.old_palace)),
                    SelectionVo(name = stringResource(R.string.observatory)),
                    SelectionVo(name = stringResource(R.string.national_park)),
                    SelectionVo(name = stringResource(R.string.hanok_village)),
                    SelectionVo(name = stringResource(R.string.temple)),
                    SelectionVo(name = stringResource(R.string.art_gallery)),
                    SelectionVo(name = stringResource(R.string.museum))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.avoid_facility),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.not_applicable)),
                    SelectionVo(name = stringResource(R.string.highway)),
                    SelectionVo(name = stringResource(R.string.railway)),
                    SelectionVo(name = stringResource(R.string.entertainment_street)),
                    SelectionVo(name = stringResource(R.string.industrial_complex)),
                    SelectionVo(name = stringResource(R.string.factory)),
                    SelectionVo(name = stringResource(R.string.garbage_incinerator)),
                    SelectionVo(name = stringResource(R.string.high_rise_building)),
                    SelectionVo(name = stringResource(R.string.building_under_construction))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
        }
        item {
            WriteInsightDetailContentView(
                title = stringResource(R.string.complex_environment_overall_review),
                onClick = {
                    // todo : 총평 작성 화면 이동
                }
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 1950)
@Composable
private fun WriteInsightInfraPagePreview1() {
    ImdangTheme {
        WriteInsightInfraPage()
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteInsightInfraPagePreview2() {
    ImdangTheme {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            WriteInsightSelectionButtons(
                title = stringResource(R.string.avoid_facility),
                items = listOf(
                    SelectionVo(name = stringResource(R.string.not_applicable)),
                    SelectionVo(name = stringResource(R.string.highway)),
                    SelectionVo(name = stringResource(R.string.railway)),
                    SelectionVo(name = stringResource(R.string.entertainment_street)),
                    SelectionVo(name = stringResource(R.string.industrial_complex)),
                    SelectionVo(name = stringResource(R.string.factory)),
                    SelectionVo(name = stringResource(R.string.garbage_incinerator)),
                    SelectionVo(name = stringResource(R.string.high_rise_building)),
                    SelectionVo(name = stringResource(R.string.building_under_construction))
                ),
                onClickItem = {
                    // todo : 아이템 선택
                }
            )
            WriteInsightDetailContentView(
                title = stringResource(R.string.complex_environment_overall_review),
                onClick = {
                    // todo : 총평 작성 화면 이동
                }
            )
        }
    }
}
