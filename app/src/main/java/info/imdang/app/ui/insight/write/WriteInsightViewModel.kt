package info.imdang.app.ui.insight.write

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.common.util.formatDate
import info.imdang.app.common.util.reformatDate
import info.imdang.app.model.insight.mapper
import info.imdang.app.util.validateVisitDate
import info.imdang.component.common.snackbar.showSnackbar
import info.imdang.component.model.SelectionVo
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.insight.ApartmentComplexDto
import info.imdang.domain.model.insight.ComplexEnvironmentDto
import info.imdang.domain.model.insight.ComplexFacilityDto
import info.imdang.domain.model.insight.FavorableNewsDto
import info.imdang.domain.model.insight.InfraDto
import info.imdang.domain.model.insight.request.WriteInsightDto
import info.imdang.domain.usecase.insight.GetInsightDetailUseCase
import info.imdang.domain.usecase.insight.UpdateInsightParams
import info.imdang.domain.usecase.insight.UpdateInsightUseCase
import info.imdang.domain.usecase.insight.WriteInsightParams
import info.imdang.domain.usecase.insight.WriteInsightUseCase
import java.io.File
import java.util.Locale
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class WriteInsightViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getInsightDetailUseCase: GetInsightDetailUseCase,
    private val writeInsightUseCase: WriteInsightUseCase,
    private val updateInsightUseCase: UpdateInsightUseCase
) : BaseViewModel() {

    val insightId: String? = savedStateHandle["insightId"]

    private val _event = MutableSharedFlow<WriteInsightEvent>()
    val event = _event.asSharedFlow()

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

    val visitDateErrorMessage = combine(visitDate) {
        it.first().validateVisitDate()
    }.toStateFlow("")

    private val visitDateValid = combine(
        visitDate,
        visitDateErrorMessage
    ) { visitDate, visitDateErrorMessage ->
        visitDate.isNotEmpty() && visitDateErrorMessage.isEmpty()
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
        coverImageFile.isValid(),
        coverImageUrl.isValid(),
        titleValid,
        address.isValid(),
        complexName.isValid(),
        visitDateValid,
        visitTimes.selectedItems.isValid(),
        trafficsMethods.selectedItems.isValid(),
        accessLimits.selectedItems.isValid(),
        summary.map { it.length in 30..200 }.toStateFlow(false)
    ) { valid ->
        (valid[0] || valid[1]) && valid.takeLast(valid.size - 2).all { it }
    }.toStateFlow(false)

    private val infraValid = combine(
        infraTraffics.selectedItems.isValid(),
        schools.selectedItems.isValid(),
        livingFacilities.selectedItems.isValid(),
        cultureFacilities.selectedItems.isValid(),
        surroundingEnvironments.selectedItems.isValid(),
        landmarks.selectedItems.isValid(),
        avoidFacilities.selectedItems.isValid()
    ) { valid ->
        valid.all { it }
    }.toStateFlow(false)

    private val complexEnvironmentValid = combine(
        buildings.selectedItems.isValid(),
        safeties.selectedItems.isValid(),
        childrenFacilities.selectedItems.isValid(),
        silverFacilities.selectedItems.isValid()
    ) { valid ->
        valid.all { it }
    }.toStateFlow(false)

    private val complexFacilityValid = combine(
        familyFacilities.selectedItems.isValid(),
        multipurposeFacilities.selectedItems.isValid(),
        leisureFacilities.selectedItems.isValid(),
        environments.selectedItems.isValid()
    ) { valid ->
        valid.all { it }
    }.toStateFlow(false)

    private val goodNewsValid = combine(
        goodNewsTraffics.selectedItems.isValid(),
        developments.selectedItems.isValid(),
        educations.selectedItems.isValid(),
        naturalEnvironments.selectedItems.isValid(),
        cultures.selectedItems.isValid(),
        industries.selectedItems.isValid(),
        policies.selectedItems.isValid()
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

    init {
        fetchInsightDetail()
    }

    private fun fetchInsightDetail() {
        insightId ?: return
        viewModelScope.launch {
            val insight = getInsightDetailUseCase(insightId)?.mapper() ?: return@launch

            // 기본 정보
            _coverImageUrl.value = insight.mainImage
            _title.value = insight.title
            _address.value = insight.address.toJibunAddress()
            _complexName.value = insight.complexName
            _latitude.value = insight.address.latitude ?: 0.0
            _longitude.value = insight.address.longitude ?: 0.0
            _visitDate.value = insight.visitAt
            visitTimes.selectItems(insight.visitTimes)
            trafficsMethods.selectItems(insight.visitMethods)
            accessLimits.selectItems(listOf(insight.access))
            _summary.value = insight.summary
            // 인프라
            insight.infra?.let {
                infraTraffics.selectItems(it.traffics)
                schools.selectItems(it.schools)
                livingFacilities.selectItems(it.lifeFacilities)
                cultureFacilities.selectItems(it.cultureFacilities)
                surroundingEnvironments.selectItems(it.surroundingEnvironments)
                landmarks.selectItems(it.landmarks)
                avoidFacilities.selectItems(it.avoidFacilities)
                _infraReview.value = it.infraReview
            }
            // 단지 환경
            insight.complexEnvironment?.let {
                buildings.selectItems(listOf(it.building))
                safeties.selectItems(listOf(it.safety))
                childrenFacilities.selectItems(listOf(it.childrenFacility))
                silverFacilities.selectItems(listOf(it.silverFacility))
                _complexFacilityReview.value = it.complexEnvironmentReview
            }
            // 단지 시설
            insight.complexFacility?.let {
                familyFacilities.selectItems(it.familyFacilities)
                multipurposeFacilities.selectItems(it.multipurposeFacilities)
                leisureFacilities.selectItems(it.leisureFacilities)
                environments.selectItems(it.environments)
                _complexFacilityReview.value = it.complexFacilityReview
            }
            // 호재
            insight.goodNews?.let {
                goodNewsTraffics.selectItems(it.traffics)
                developments.selectItems(it.developments)
                educations.selectItems(it.educations)
                naturalEnvironments.selectItems(it.naturalEnvironments)
                cultures.selectItems(it.cultures)
                industries.selectItems(it.industries)
                policies.selectItems(it.policies)
                _goodNewsReview.value = it.goodNewsReview
            }

            updateProgress()
        }
    }

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
        progress += if (infraReview.isValid().value) 10 else 0
        progress += if (complexEnvironmentValid.value) 10 else 0
        progress += if (complexEnvironmentReview.isValid().value) 10 else 0
        progress += if (complexFacilityValid.value) 10 else 0
        progress += if (complexFacilityReview.isValid().value) 10 else 0
        progress += if (goodNewsValid.value) 10 else 0
        progress += if (goodNewsReview.isValid().value) 10 else 0
        _progress.value = "${String.format(Locale.KOREA, "%02d", progress)}%"
    }

    fun updateSelectedPage(page: Int) {
        _selectedPage.value = page
    }

    fun updateCoverImageFile(file: File?) {
        _coverImageFile.value = file
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

    fun updateSummary(summary: String) {
        _summary.value = summary
    }

    fun updateInfraReview(review: String) {
        _infraReview.value = review
    }

    fun updateComplexEnvironmentReview(review: String) {
        _complexEnvironmentReview.value = review
    }

    fun updateComplexFacilityReview(review: String) {
        _complexFacilityReview.value = review
    }

    fun updateGoodNewsReview(review: String) {
        _goodNewsReview.value = review
    }

    fun writeInsight() {
        val writeInsightDto = WriteInsightDto(
            insightId = insightId,
            score = progress.value.replace("%", "").toInt(),
            title = title.value,
            address = AddressDto(
                siDo = address.value.split(" ")[0],
                siGunGu = address.value.split(" ")[1],
                eupMyeonDong = address.value.split(" ")[2],
                roadName = "",
                buildingNumber = address.value.split(" ")[3],
                detail = "",
                latitude = latitude.value,
                longitude = longitude.value
            ),
            apartmentComplex = ApartmentComplexDto(
                name = complexName.value
            ),
            visitAt = visitDate.value.formatDate(
                fromFormat = "yyyy.MM.dd",
                toFormat = "yyyy-MM-dd"
            ),
            visitTimes = visitTimes.formattedSelectedItems(),
            visitMethods = trafficsMethods.formattedSelectedItems(),
            access = accessLimits.selectedSingleItem(),
            summary = summary.value,
            infra = InfraDto(
                transportations = infraTraffics.formattedSelectedItems(),
                schoolDistricts = schools.formattedSelectedItems(),
                amenities = livingFacilities.formattedSelectedItems(),
                facilities = cultureFacilities.formattedSelectedItems(),
                surroundings = surroundingEnvironments.formattedSelectedItems(),
                landmarks = landmarks.formattedSelectedItems(),
                unpleasantFacilities = avoidFacilities.formattedSelectedItems(),
                text = infraReview.value
            ),
            complexEnvironment = ComplexEnvironmentDto(
                buildingCondition = buildings.selectedSingleItem(),
                security = safeties.selectedSingleItem(),
                childrenFacility = childrenFacilities.selectedSingleItem(),
                seniorFacility = silverFacilities.selectedSingleItem(),
                text = complexEnvironmentReview.value
            ),
            complexFacility = ComplexFacilityDto(
                familyFacilities = familyFacilities.formattedSelectedItems(),
                multipurposeFacilities = multipurposeFacilities
                    .formattedSelectedItems(),
                leisureFacilities = leisureFacilities.formattedSelectedItems(),
                surroundings = environments.formattedSelectedItems(),
                text = complexFacilityReview.value
            ),
            favorableNews = FavorableNewsDto(
                transportations = goodNewsTraffics.formattedSelectedItems(),
                developments = developments.formattedSelectedItems(),
                educations = educations.formattedSelectedItems(),
                environments = naturalEnvironments.formattedSelectedItems(),
                cultures = cultures.formattedSelectedItems(),
                industries = industries.formattedSelectedItems(),
                policies = policies.formattedSelectedItems(),
                text = goodNewsReview.value
            )
        )

        viewModelScope.launch {
            if (insightId == null) {
                // 인사이트 작성
                writeInsightUseCase(
                    WriteInsightParams(
                        writeInsightDto = writeInsightDto,
                        mainImage = coverImageFile.value ?: return@launch
                    ),
                    onError = {
                        it.message?.let {
                            launch {
                                showSnackbar(it)
                            }
                        }
                    }
                )?.let {
                    _event.emit(WriteInsightEvent.WriteInsightComplete(it.insightId))
                }
            } else {
                // 인사이트 수정
                updateInsightUseCase(
                    UpdateInsightParams(
                        writeInsightDto = writeInsightDto,
                        mainImage = coverImageFile.value
                    ),
                    onError = {
                        it.message?.let {
                            launch {
                                showSnackbar(it)
                            }
                        }
                    }
                )?.let {
                    _event.emit(WriteInsightEvent.WriteInsightComplete(it.insightId))
                }
            }
        }
    }

    private fun WriteInsightSelectionItems.formattedSelectedItems() = selectedItems
        .toStateFlow(emptyList()).value
        .map {
            it.name.replace(" ", "_")
        }

    private fun WriteInsightSelectionItems.selectedSingleItem() =
        formattedSelectedItems().firstOrNull() ?: ""
}
