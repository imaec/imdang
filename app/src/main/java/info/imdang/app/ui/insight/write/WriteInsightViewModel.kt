package info.imdang.app.ui.insight.write

import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.common.util.reformatDate
import info.imdang.app.util.validateVisitDate
import info.imdang.component.model.SelectionVo
import java.io.File
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class WriteInsightViewModel @Inject constructor() : BaseViewModel() {

    private val _selectedPage = MutableStateFlow(0)
    val selectedPage = _selectedPage.asStateFlow()

    private val _progress = MutableStateFlow("00%")
    val progress = _progress.asStateFlow()

    /** 기본정보 */
    private val _coverImageFile = MutableStateFlow<File?>(null)
    val coverImageFile = _coverImageFile.asStateFlow()

    private val _coverImageUrl = MutableStateFlow<String?>(null)
    val coverImageUrl = _coverImageUrl.asStateFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val titleValid = combine(title) {
        it.first().isNotBlank()
    }.toStateFlow(false)

    private val _isTitleFocused = MutableStateFlow(false)
    val isTitleFocused = _isTitleFocused.asStateFlow()

    private val _address = MutableStateFlow("")
    val address = _address.asStateFlow()

    private val _complexName = MutableStateFlow("")
    val complexName = _complexName.asStateFlow()

    private val _latitude = MutableStateFlow(0.0)
    private val latitude = _latitude.asStateFlow()

    private val _longitude = MutableStateFlow(0.0)
    private val longitude = _longitude.asStateFlow()

    private val _visitDate = MutableStateFlow("")
    val visitDate = _visitDate.asStateFlow()

    private val visitDateValid = combine(visitDate) {
        it.first().validateVisitDate().isEmpty()
    }.toStateFlow(false)

    private val _isVisitDateFocused = MutableStateFlow(false)
    val isVisitDateFocused = _isVisitDateFocused.asStateFlow()

    val visitTimes = WriteInsightSelectionItems()
    val trafficsMethods = WriteInsightSelectionItems()
    val accessLimits = WriteInsightSelectionItems(isSingleSelection = true)

    private val _summary = MutableStateFlow("")
    val summary = _summary.asStateFlow()

    val isValidButtonEnabled = combine(
        isTitleFocused,
        isVisitDateFocused,
        titleValid,
        visitDateValid
    ) { isTitleFocused, isVisitDateFocused, titleValid, visitDateValid ->
        if (isTitleFocused) return@combine titleValid
        if (isVisitDateFocused) return@combine visitDateValid
        false
    }.toStateFlow(false)

    /** 인프라 */
    val infraTraffics = WriteInsightSelectionItems()
    val schools = WriteInsightSelectionItems()
    val livingFacilities = WriteInsightSelectionItems()
    val cultureFacilities = WriteInsightSelectionItems()
    val surroundingEnvironments = WriteInsightSelectionItems()
    val landmarks = WriteInsightSelectionItems()
    val avoidFacilities = WriteInsightSelectionItems()

    private val _infraReview = MutableStateFlow("")
    val infraReview = _infraReview.asStateFlow()

    /** 단지 환경 */
    val buildings = WriteInsightSelectionItems(isSingleSelection = true)
    val safeties = WriteInsightSelectionItems(isSingleSelection = true)
    val childrenFacilities = WriteInsightSelectionItems(isSingleSelection = true)
    val silverFacilities = WriteInsightSelectionItems(isSingleSelection = true)

    private val _complexEnvironmentReview = MutableStateFlow("")
    val complexEnvironmentReview = _complexEnvironmentReview.asStateFlow()

    /** 단지 시설 */
    val familyFacilities = WriteInsightSelectionItems()
    val multipurposeFacilities = WriteInsightSelectionItems()
    val leisureFacilities = WriteInsightSelectionItems()
    val environments = WriteInsightSelectionItems()

    private val _complexFacilityReview = MutableStateFlow("")
    val complexFacilityReview = _complexFacilityReview.asStateFlow()

    /** 호재 */
    val goodNewsTraffics = WriteInsightSelectionItems()
    val developments = WriteInsightSelectionItems()
    val educations = WriteInsightSelectionItems()
    val naturalEnvironments = WriteInsightSelectionItems()
    val cultures = WriteInsightSelectionItems()
    val industries = WriteInsightSelectionItems()
    val policies = WriteInsightSelectionItems()

    private val _goodNewsReview = MutableStateFlow("")
    val goodNewsReview = _goodNewsReview.asStateFlow()

    /** 페이지별 작성 완료 여부 **/
    private val basicInfoValid = combine(
//        coverImageFile.isCheckVisible(),
//        coverImageUrl.isCheckVisible(),
        titleValid,
//        address.isCheckVisible(),
//        complexName.isCheckVisible(),
        visitDateValid,
        visitTimes.selectedItems.isCheckVisible(),
        trafficsMethods.selectedItems.isCheckVisible(),
        accessLimits.selectedItems.isCheckVisible()
//        summary.isCheckVisible()
    ) { valid ->
        // todo : coverImage 업로드 구현 후 사용
//        (valid[0] || valid[1]) && valid.takeLast(valid.size - 2).all { it }
        valid.all { it }
    }.toStateFlow(false)

    private val infraValid = combine(
        infraTraffics.selectedItems.isCheckVisible(),
        schools.selectedItems.isCheckVisible(),
        livingFacilities.selectedItems.isCheckVisible(),
        cultureFacilities.selectedItems.isCheckVisible(),
        surroundingEnvironments.selectedItems.isCheckVisible(),
        landmarks.selectedItems.isCheckVisible(),
        avoidFacilities.selectedItems.isCheckVisible()
    ) { valid ->
        valid.all { it }
    }.toStateFlow(false)

    private val complexEnvironmentValid = combine(
        buildings.selectedItems.isCheckVisible(),
        safeties.selectedItems.isCheckVisible(),
        childrenFacilities.selectedItems.isCheckVisible(),
        silverFacilities.selectedItems.isCheckVisible()
    ) { valid ->
        valid.all { it }
    }.toStateFlow(false)

    private val complexFacilityValid = combine(
        familyFacilities.selectedItems.isCheckVisible(),
        multipurposeFacilities.selectedItems.isCheckVisible(),
        leisureFacilities.selectedItems.isCheckVisible(),
        environments.selectedItems.isCheckVisible()
    ) { valid ->
        valid.all { it }
    }.toStateFlow(false)

    private val goodNewsValid = combine(
        goodNewsTraffics.selectedItems.isCheckVisible(),
        developments.selectedItems.isCheckVisible(),
        educations.selectedItems.isCheckVisible(),
        naturalEnvironments.selectedItems.isCheckVisible(),
        cultures.selectedItems.isCheckVisible(),
        industries.selectedItems.isCheckVisible(),
        policies.selectedItems.isCheckVisible()
    ) { valid ->
        valid.all { it }
    }.toStateFlow(false)

    val isButtonEnabled = combine(
        selectedPage,
        basicInfoValid,
        infraValid,
        complexEnvironmentValid,
        complexFacilityValid,
        goodNewsValid
    ) {
        when (selectedPage.value) {
            0 -> basicInfoValid.value
            1 -> infraValid.value
            2 -> complexEnvironmentValid.value
            3 -> complexFacilityValid.value
            4 -> goodNewsValid.value
            else -> false
        }
    }.toStateFlow(false)

    fun initSelectionItems(
        visitTimes: List<SelectionVo>,
        trafficsMethods: List<SelectionVo>,
        accessLimits: List<SelectionVo>,
        infraTraffics: List<SelectionVo>,
        schools: List<SelectionVo>,
        livingFacilities: List<SelectionVo>,
        cultureFacilities: List<SelectionVo>,
        surroundingEnvironments: List<SelectionVo>,
        landmarks: List<SelectionVo>,
        avoidFacilities: List<SelectionVo>,
        buildings: List<SelectionVo>,
        safeties: List<SelectionVo>,
        childrenFacilities: List<SelectionVo>,
        silverFacilities: List<SelectionVo>,
        familyFacilities: List<SelectionVo>,
        multipurposeFacilities: List<SelectionVo>,
        leisureFacilities: List<SelectionVo>,
        environments: List<SelectionVo>,
        goodNewsTraffics: List<SelectionVo>,
        developments: List<SelectionVo>,
        educations: List<SelectionVo>,
        naturalEnvironments: List<SelectionVo>,
        cultures: List<SelectionVo>,
        industries: List<SelectionVo>,
        policies: List<SelectionVo>
    ) {
        if (this.visitTimes.isInit()) return

        this.visitTimes.setItems(visitTimes)
        this.trafficsMethods.setItems(trafficsMethods)
        this.accessLimits.setItems(accessLimits)
        this.infraTraffics.setItems(infraTraffics)
        this.schools.setItems(schools)
        this.livingFacilities.setItems(livingFacilities)
        this.cultureFacilities.setItems(cultureFacilities)
        this.surroundingEnvironments.setItems(surroundingEnvironments)
        this.landmarks.setItems(landmarks)
        this.avoidFacilities.setItems(avoidFacilities)
        this.buildings.setItems(buildings)
        this.safeties.setItems(safeties)
        this.childrenFacilities.setItems(childrenFacilities)
        this.silverFacilities.setItems(silverFacilities)
        this.familyFacilities.setItems(familyFacilities)
        this.multipurposeFacilities.setItems(multipurposeFacilities)
        this.leisureFacilities.setItems(leisureFacilities)
        this.environments.setItems(environments)
        this.goodNewsTraffics.setItems(goodNewsTraffics)
        this.developments.setItems(developments)
        this.educations.setItems(educations)
        this.naturalEnvironments.setItems(naturalEnvironments)
        this.cultures.setItems(cultures)
        this.industries.setItems(industries)
        this.policies.setItems(policies)
    }

    fun updateProgress() {
        var progress = if (basicInfoValid.value) 20 else 0
        progress += if (infraValid.value) 10 else 0
        progress += if (infraReview.isCheckVisible().value) 10 else 0
        progress += if (complexEnvironmentValid.value) 10 else 0
        progress += if (complexEnvironmentReview.isCheckVisible().value) 10 else 0
        progress += if (complexFacilityValid.value) 10 else 0
        progress += if (complexFacilityReview.isCheckVisible().value) 10 else 0
        progress += if (goodNewsValid.value) 10 else 0
        progress += if (goodNewsReview.isCheckVisible().value) 10 else 0
        _progress.value = "${String.format(Locale.KOREA, "%02d", progress)}%"
    }

    fun updateSelectedPage(page: Int) {
        _selectedPage.value = page
    }

    fun updateTitle(title: String) {
        _title.value = title
    }

    fun updateTitleFocused(isFocused: Boolean) {
        _isTitleFocused.value = isFocused
    }

    fun updateAddress(address: String) {
        _address.value = address
    }

    fun updateComplexName(name: String) {
        _complexName.value = name
    }

    fun updateLatitude(latitude: Double) {
        _latitude.value = latitude
    }

    fun updateLongitude(longitude: Double) {
        _longitude.value = longitude
    }

    fun updateVisitDate(visitDate: String) {
        _visitDate.value = reformatDate(visitDate)
    }

    fun updateVisitDateFocused(isFocused: Boolean) {
        _isVisitDateFocused.value = isFocused
    }
}
