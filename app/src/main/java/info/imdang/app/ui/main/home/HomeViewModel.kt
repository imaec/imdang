package info.imdang.app.ui.main.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.common.util.diffDays
import info.imdang.app.common.util.isToday
import info.imdang.app.common.util.snakeToCamelCase
import info.imdang.app.common.util.toLocalDate
import info.imdang.app.model.common.PagingDirection
import info.imdang.app.model.common.PagingProperty
import info.imdang.app.model.complex.VisitedComplexVo
import info.imdang.app.model.complex.mapper
import info.imdang.app.model.coupon.CouponVo
import info.imdang.app.model.coupon.mapper
import info.imdang.app.model.insight.ExchangeRequestStatus
import info.imdang.app.model.insight.ExchangeType
import info.imdang.app.model.insight.InsightVo
import info.imdang.app.model.insight.mapper
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.coupon.CouponDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.usecase.complex.GetVisitedComplexesUseCase
import info.imdang.domain.usecase.coupon.GetCouponUseCase
import info.imdang.domain.usecase.coupon.IssueCouponUseCase
import info.imdang.domain.usecase.home.GetCloseTimeOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.GetFirstDateOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.SetCloseTimeOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.SetFirstDateOfHomeFreePassUseCase
import info.imdang.domain.usecase.insight.GetInsightsByComplexParams
import info.imdang.domain.usecase.insight.GetInsightsByComplexUseCase
import info.imdang.domain.usecase.insight.GetInsightsByDateParams
import info.imdang.domain.usecase.insight.GetInsightsByDateUseCase
import info.imdang.domain.usecase.insight.GetInsightsUseCase
import info.imdang.domain.usecase.myexchange.GetExchangesParams
import info.imdang.domain.usecase.myexchange.GetRequestExchangesUseCase
import info.imdang.domain.usecase.myexchange.GetRequestedExchangesUseCase
import info.imdang.domain.usecase.mypage.GetMyPageInfoUseCase
import info.imdang.domain.usecase.notification.HasNewNotificationUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class HomeViewModel @Inject constructor(
    private val hasNewNotificationUseCase: HasNewNotificationUseCase,
    private val getCouponUseCase: GetCouponUseCase,
    private val getMyPageInfoUseCase: GetMyPageInfoUseCase,
    private val getFirstDateOfHomeFreePassUseCase: GetFirstDateOfHomeFreePassUseCase,
    private val setFirstDateOfHomeFreePassUseCase: SetFirstDateOfHomeFreePassUseCase,
    private val getCloseTimeOfHomeFreePassUseCase: GetCloseTimeOfHomeFreePassUseCase,
    private val setCloseTimeOfHomeFreePassUseCase: SetCloseTimeOfHomeFreePassUseCase,
    private val issueCouponUseCase: IssueCouponUseCase,
    private val getVisitedComplexesUseCase: GetVisitedComplexesUseCase,
    private val getInsightsByComplexUseCase: GetInsightsByComplexUseCase,
    private val getInsightsByDateUseCase: GetInsightsByDateUseCase,
    private val getInsightsUseCase: GetInsightsUseCase,
    private val getRequestExchangesUseCase: GetRequestExchangesUseCase,
    private val getRequestedExchangesUseCase: GetRequestedExchangesUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    private val _hasNewNotification = MutableStateFlow(false)
    val hasNewNotification = _hasNewNotification.asStateFlow()

    private val _nickname = MutableStateFlow("")
    val nickname = _nickname.asStateFlow()

    private val _isShowTooltip = MutableStateFlow(false)
    val isShowTooltip = _isShowTooltip.asStateFlow()

    /** 탐색 */
    protected val _visitedComplexes = MutableStateFlow<List<VisitedComplexVo>>(emptyList())
    val visitedComplexes = _visitedComplexes.asStateFlow()

    protected val _insightsByComplex = MutableStateFlow<List<InsightVo>>(emptyList())
    val insightsByComplex = _insightsByComplex.asStateFlow()

    protected val _newInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val newInsights = _newInsights.asStateFlow()

    protected val _recommendInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val recommendInsights = _recommendInsights.asStateFlow()

    /** 교환소 */
    private val _currentExchangeType = MutableStateFlow(ExchangeType.REQUEST)
    val currentExchangeType = _currentExchangeType.asStateFlow()

    protected val _coupon = MutableStateFlow<CouponVo?>(null)
    val coupon = _coupon.asStateFlow()

    private val _selectedExchangeStatus = MutableStateFlow(ExchangeRequestStatus.PENDING)
    val selectedExchangeStatus = _selectedExchangeStatus.asStateFlow()

    protected val _requestExchangeInsights = MutableStateFlow<PagingData<InsightVo>>(
        PagingData.empty()
    )
    val requestExchangeInsights = _requestExchangeInsights.asStateFlow()

    protected val _requestedExchangeInsights = MutableStateFlow<PagingData<InsightVo>>(
        PagingData.empty()
    )
    val requestedExchangeInsights = _requestedExchangeInsights.asStateFlow()

    protected val _exchangeInsightCount = MutableStateFlow(0)
    val exchangeInsightCount = _exchangeInsightCount.asStateFlow()

    init {
        fetchHasNewNotification()
        fetchCoupon()

        // 탐색
        fetchVisitedComplexes()
        fetchInsights()
        fetchRecommendInsights()
    }

    private fun fetchHasNewNotification() {
        viewModelScope.launch {
            _hasNewNotification.value = hasNewNotificationUseCase(Unit) ?: false
        }
    }

    private fun fetchCoupon() {
        viewModelScope.launch {
            _nickname.value = getMyPageInfoUseCase(Unit)?.nickname ?: ""
            _coupon.value = getCouponUseCase(Unit)?.let(CouponDto::mapper)
            val couponCount = coupon.value ?: 0
            if (couponCount == 0 && nickname.value.isNotBlank()) showFreePassPopup()
        }
    }

    private fun showFreePassPopup() {
        viewModelScope.launch {
            val firstOpenDate = getFirstDateOfHomeFreePassUseCase(Unit)?.takeIf { it != 0L }
            if (firstOpenDate == null) {
                setFirstDateOfHomeFreePassUseCase(System.currentTimeMillis())
                _event.emit(HomeEvent.ShowHomeFreePassBottomSheet)
            } else if (firstOpenDate.diffDays() < 3) {
                val closeDate = (getCloseTimeOfHomeFreePassUseCase(Unit) ?: 0).toLocalDate()
                if (!closeDate.isToday()) {
                    _event.emit(HomeEvent.ShowHomeFreePassBottomSheet)
                }
            }
        }
    }

    private fun fetchVisitedComplexes() {
        viewModelScope.launch {
            _visitedComplexes.value =
                getVisitedComplexesUseCase(Unit)?.mapIndexed { index, visitedComplexDto ->
                    visitedComplexDto.mapper(isSelected = index == 0)
                } ?: emptyList()
            fetchInsightsByComplex()
        }
    }

    private fun fetchInsightsByComplex() {
        viewModelScope.launch {
            val selectedComplexName = visitedComplexes.value.firstOrNull {
                it.isSelected
            }?.complexName ?: return@launch
            _insightsByComplex.value = getInsightsByComplexUseCase(
                GetInsightsByComplexParams(
                    complexName = selectedComplexName,
                    pagingParams = PagingParams(
                        page = 1,
                        size = 3
                    )
                )
            )?.content?.map(InsightDto::mapper)?.take(3) ?: emptyList()
        }
    }

    private fun fetchInsights() {
        viewModelScope.launch {
            _newInsights.value = getInsightsByDateUseCase(
                GetInsightsByDateParams(pagingParams = PagingParams(size = 5))
            )?.content?.map(InsightDto::mapper)?.take(5) ?: emptyList()
        }
    }

    private fun fetchRecommendInsights() {
        viewModelScope.launch {
            val recommendInsights = getInsightsUseCase(
                PagingParams(
                    size = 10,
                    direction = PagingDirection.DESC.name,
                    properties = listOf(PagingProperty.RECOMMENDED_COUNT.name.snakeToCamelCase())
                )
            )?.content?.map(InsightDto::mapper) ?: emptyList()
            _recommendInsights.value = recommendInsights
        }
    }

    fun setCloseTimeOfHomeFreePass() {
        viewModelScope.launch {
            setCloseTimeOfHomeFreePassUseCase(System.currentTimeMillis())
        }
    }

    fun showTooltip() {
        viewModelScope.launch {
            issueCouponUseCase(Unit)?.let {
                _isShowTooltip.value = true
                fetchCoupon()
            }
        }
    }

    fun hideTooltip() {
        _isShowTooltip.value = false
    }

    fun onClickVisitedComplex(complexVo: VisitedComplexVo) {
        updateVisitedComplexes(visitedComplexes.value.indexOf(complexVo))
    }

    fun updateVisitedComplexes(selectedIndex: Int) {
        _visitedComplexes.value = visitedComplexes.value.mapIndexed { index, visitedComplexVo ->
            visitedComplexVo.copy(isSelected = index == selectedIndex)
        }
        fetchInsightsByComplex()
    }

    fun getSelectedComplexIndex() = visitedComplexes.value.indexOfFirst { it.isSelected }

    private fun fetchRequestExchange() {
        viewModelScope.launch {
            getRequestExchangesUseCase(
                GetExchangesParams(
                    exchangeRequestStatus = selectedExchangeStatus.value.name,
                    pagingParams = PagingParams(
                        totalCountListener = {
                            _exchangeInsightCount.value = it
                        }
                    )
                )
            )
                ?.cachedIn(this)
                ?.collect {
                    _requestExchangeInsights.value = it.map(InsightDto::mapper)
                }
        }
    }

    private fun fetchRequestedExchange() {
        viewModelScope.launch {
            getRequestedExchangesUseCase(
                GetExchangesParams(
                    exchangeRequestStatus = selectedExchangeStatus.value.name,
                    pagingParams = PagingParams(
                        totalCountListener = {
                            _exchangeInsightCount.value = it
                        }
                    )
                )
            )
                ?.cachedIn(this)
                ?.collect {
                    _requestedExchangeInsights.value = it.map(InsightDto::mapper)
                }
        }
    }

    fun updateExchangeType(exchangeType: ExchangeType) {
        _currentExchangeType.value = exchangeType

        when (exchangeType) {
            ExchangeType.REQUEST -> fetchRequestExchange()
            ExchangeType.REQUESTED -> fetchRequestedExchange()
        }
    }

    fun onClickExchangeStatus(exchangeStatus: ExchangeRequestStatus) {
        _selectedExchangeStatus.value = exchangeStatus

        when (currentExchangeType.value) {
            ExchangeType.REQUEST -> fetchRequestExchange()
            ExchangeType.REQUESTED -> fetchRequestedExchange()
        }
    }
}
