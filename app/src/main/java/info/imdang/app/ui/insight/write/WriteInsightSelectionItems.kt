package info.imdang.app.ui.insight.write

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import info.imdang.component.model.SelectionVo
import info.imdang.resource.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class WriteInsightSelectionItems(val isSingleSelection: Boolean = false) {

    private val resetItems = listOf("해당 없음", "잘 모르겠어요")

    private val _items = MutableStateFlow<List<SelectionVo>>(emptyList())
    val items = _items.asStateFlow()

    val selectedItems: Flow<List<SelectionVo>>
        get() = items.map { it.filter { item -> item.isSelected } }

    val isValid: Flow<Boolean>
        get() = selectedItems.map { it.isNotEmpty() }

    fun isInit() = items.value.isNotEmpty()

    fun setItems(items: List<SelectionVo>) {
        _items.value = items
    }

    fun selectItem(item: SelectionVo, onShowResetDialog: (resetItem: String) -> Unit = {}) {
        if (item.isReset()) {
            onShowResetDialog(item.name)
            return
        }
        _items.value = items.value.map {
            if (isSingleSelection) {
                it.copy(
                    isSelected = it.name == item.name && !it.isSelected
                )
            } else {
                if (it.name == item.name) {
                    it.copy(isSelected = !it.isSelected)
                } else {
                    it.copy(isSelected = it.isSelected && !resetItems.contains(it.name))
                }
            }
        }
    }

    fun selectItems(selectedItems: List<String>) {
        _items.value = items.value.map { item ->
            item.copy(isSelected = selectedItems.any { item.name == it })
        }
    }

    fun confirmReset(resetItem: String) {
        _items.value = items.value.map {
            it.copy(isSelected = it.name == resetItem && !it.isSelected)
        }
    }

    private fun SelectionVo.isReset() = items.value.any { it.isSelected } &&
        resetItems.contains(name) &&
        !isSelected
}

object BasicInfoItems {
    @Composable
    fun visitTimes() = listOf(
        SelectionVo(name = stringResource(R.string.morning)),
        SelectionVo(name = stringResource(R.string.day)),
        SelectionVo(name = stringResource(R.string.evening)),
        SelectionVo(name = stringResource(R.string.night))
    )

    @Composable
    fun traffics() = listOf(
        SelectionVo(name = stringResource(R.string.car)),
        SelectionVo(name = stringResource(R.string.public_traffic)),
        SelectionVo(name = stringResource(R.string.walk))
    )

    @Composable
    fun accessLimits() = listOf(
        SelectionVo(name = stringResource(R.string.limited)),
        SelectionVo(name = stringResource(R.string.need_permission)),
        SelectionVo(name = stringResource(R.string.free_access))
    )
}

object InfraItems {
    @Composable
    fun traffics() = listOf(
        SelectionVo(name = stringResource(R.string.not_applicable)),
        SelectionVo(name = stringResource(R.string.parking_convenient)),
        SelectionVo(name = stringResource(R.string.around_subway_station)),
        SelectionVo(name = stringResource(R.string.around_the_bus_stop))
    )

    @Composable
    fun schools() = listOf(
        SelectionVo(name = stringResource(R.string.not_applicable)),
        SelectionVo(name = stringResource(R.string.apartment_with_elementary_school)),
        SelectionVo(name = stringResource(R.string.daycare_center)),
        SelectionVo(name = stringResource(R.string.middle_school)),
        SelectionVo(name = stringResource(R.string.high_school)),
        SelectionVo(name = stringResource(R.string.academy_district))
    )

    @Composable
    fun livingFacilities() = listOf(
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
    )

    @Composable
    fun cultureFacilities() = listOf(
        SelectionVo(name = stringResource(R.string.not_applicable)),
        SelectionVo(name = stringResource(R.string.library)),
        SelectionVo(name = stringResource(R.string.cinema)),
        SelectionVo(name = stringResource(R.string.gym)),
        SelectionVo(name = stringResource(R.string.health_center)),
        SelectionVo(name = stringResource(R.string.swimming_pool)),
        SelectionVo(name = stringResource(R.string.badminton_court)),
        SelectionVo(name = stringResource(R.string.tennis_court)),
        SelectionVo(name = stringResource(R.string.golf_driving_range))
    )

    @Composable
    fun surroundingEnvironments() = listOf(
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
    )

    @Composable
    fun landmarks() = listOf(
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
    )

    @Composable
    fun avoidFacilities() = listOf(
        SelectionVo(name = stringResource(R.string.not_applicable)),
        SelectionVo(name = stringResource(R.string.highway)),
        SelectionVo(name = stringResource(R.string.railway)),
        SelectionVo(name = stringResource(R.string.entertainment_street)),
        SelectionVo(name = stringResource(R.string.industrial_complex)),
        SelectionVo(name = stringResource(R.string.factory)),
        SelectionVo(name = stringResource(R.string.garbage_incinerator)),
        SelectionVo(name = stringResource(R.string.high_rise_building)),
        SelectionVo(name = stringResource(R.string.building_under_construction))
    )
}

object ComplexEnvironmentItems {
    @Composable
    fun buildings() = listOf(
        SelectionVo(name = stringResource(R.string.unknown)),
        SelectionVo(name = stringResource(R.string.best)),
        SelectionVo(name = stringResource(R.string.good)),
        SelectionVo(name = stringResource(R.string.normal)),
        SelectionVo(name = stringResource(R.string.not_good))
    )

    @Composable
    fun safeties() = listOf(
        SelectionVo(name = stringResource(R.string.unknown)),
        SelectionVo(name = stringResource(R.string.best)),
        SelectionVo(name = stringResource(R.string.good)),
        SelectionVo(name = stringResource(R.string.normal)),
        SelectionVo(name = stringResource(R.string.not_good))
    )

    @Composable
    fun childrenFacilities() = listOf(
        SelectionVo(name = stringResource(R.string.unknown)),
        SelectionVo(name = stringResource(R.string.best)),
        SelectionVo(name = stringResource(R.string.good)),
        SelectionVo(name = stringResource(R.string.normal)),
        SelectionVo(name = stringResource(R.string.not_good))
    )

    @Composable
    fun silverFacilities() = listOf(
        SelectionVo(name = stringResource(R.string.unknown)),
        SelectionVo(name = stringResource(R.string.best)),
        SelectionVo(name = stringResource(R.string.good)),
        SelectionVo(name = stringResource(R.string.normal)),
        SelectionVo(name = stringResource(R.string.not_good))
    )
}

object ComplexFacilityItems {
    @Composable
    fun familyFacilities() = listOf(
        SelectionVo(name = stringResource(R.string.not_applicable)),
        SelectionVo(name = stringResource(R.string.daycare_center)),
        SelectionVo(name = stringResource(R.string.senior_center))
    )

    @Composable
    fun multipurposeFacilities() = listOf(
        SelectionVo(name = stringResource(R.string.not_applicable)),
        SelectionVo(name = stringResource(R.string.multipurpose_room)),
        SelectionVo(name = stringResource(R.string.resident_representative_conference_room)),
        SelectionVo(name = stringResource(R.string.public_laundry)),
        SelectionVo(name = stringResource(R.string.public_lounge))
    )

    @Composable
    fun leisureFacilities() = listOf(
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
    )

    @Composable
    fun environments() = listOf(
        SelectionVo(name = stringResource(R.string.not_applicable)),
        SelectionVo(name = stringResource(R.string.lawn)),
        SelectionVo(name = stringResource(R.string.sculpture)),
        SelectionVo(name = stringResource(R.string.bench)),
        SelectionVo(name = stringResource(R.string.tables_and_chairs)),
        SelectionVo(name = stringResource(R.string.fountain))
    )
}

object GoodNewsItems {
    @Composable
    fun traffics() = listOf(
        SelectionVo(name = stringResource(R.string.unknown)),
        SelectionVo(name = stringResource(R.string.subway_opens)),
        SelectionVo(name = stringResource(R.string.new_high_speed_rail_station_established)),
        SelectionVo(name = stringResource(R.string.transportation_hub_designation))
    )

    @Composable
    fun developments() = listOf(
        SelectionVo(name = stringResource(R.string.unknown)),
        SelectionVo(name = stringResource(R.string.redevelopment)),
        SelectionVo(name = stringResource(R.string.reconstruction)),
        SelectionVo(name = stringResource(R.string.remodeling)),
        SelectionVo(name = stringResource(R.string.nearby_new_town_development)),
        SelectionVo(name = stringResource(R.string.complex_development)),
        SelectionVo(name = stringResource(R.string.large_shopping_mall)),
        SelectionVo(name = stringResource(R.string.department_store)),
        SelectionVo(name = stringResource(R.string.large_office_complex))
    )

    @Composable
    fun educations() = listOf(
        SelectionVo(name = stringResource(R.string.unknown)),
        SelectionVo(name = stringResource(R.string.new_elementary_school)),
        SelectionVo(name = stringResource(R.string.new_high_school)),
        SelectionVo(name = stringResource(R.string.special_high_school)),
        SelectionVo(name = stringResource(R.string.private_high_school)),
        SelectionVo(name = stringResource(R.string.international_school)),
        SelectionVo(name = stringResource(R.string.college_campus))
    )

    @Composable
    fun naturalEnvironments() = listOf(
        SelectionVo(name = stringResource(R.string.unknown)),
        SelectionVo(name = stringResource(R.string.large_park)),
        SelectionVo(name = stringResource(R.string.river_restoration)),
        SelectionVo(name = stringResource(R.string.lake_restoration))
    )

    @Composable
    fun cultures() = listOf(
        SelectionVo(name = stringResource(R.string.unknown)),
        SelectionVo(name = stringResource(R.string.large_hospital)),
        SelectionVo(name = stringResource(R.string.cultural_center)),
        SelectionVo(name = stringResource(R.string.library)),
        SelectionVo(name = stringResource(R.string.performance_hall)),
        SelectionVo(name = stringResource(R.string.gym))
    )

    @Composable
    fun industries() = listOf(
        SelectionVo(name = stringResource(R.string.unknown)),
        SelectionVo(name = stringResource(R.string.industry_complex)),
        SelectionVo(name = stringResource(R.string.corporate_relocation)),
        SelectionVo(name = stringResource(R.string.startup_complex))
    )

    @Composable
    fun policies() = listOf(
        SelectionVo(name = stringResource(R.string.unknown)),
        SelectionVo(name = stringResource(R.string.speculative_overheating_district_unlocked)),
        SelectionVo(name = stringResource(R.string.deregulation)),
        SelectionVo(name = stringResource(R.string.special_zone_designation)),
        SelectionVo(name = stringResource(R.string.job_creation_policy))
    )
}
