package info.imdang.app.ui.main.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.common.util.diffDays
import info.imdang.app.common.util.isToday
import info.imdang.app.common.util.toLocalDate
import info.imdang.domain.usecase.coupon.GetCouponUseCase
import info.imdang.domain.usecase.coupon.IssueCouponUseCase
import info.imdang.domain.usecase.home.GetCloseTimeOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.GetFirstDateOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.SetCloseTimeOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.SetFirstDateOfHomeFreePassUseCase
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
    private val issueCouponUseCase: IssueCouponUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    private val _hasNewNotification = MutableStateFlow(false)
    val hasNewNotification = _hasNewNotification.asStateFlow()

    private val _nickname = MutableStateFlow("")
    val nickname = _nickname.asStateFlow()

    private val _isShowTooltip = MutableStateFlow(false)
    val isShowTooltip = _isShowTooltip.asStateFlow()

    init {
        fetchHasNewNotification()
        fetchCoupon()
    }

    private fun fetchHasNewNotification() {
        viewModelScope.launch {
            _hasNewNotification.value = hasNewNotificationUseCase(Unit) ?: false
        }
    }

    private fun fetchCoupon() {
        viewModelScope.launch {
            val couponCount = getCouponUseCase(Unit)?.couponCount ?: 0
            _nickname.value = getMyPageInfoUseCase(Unit)?.nickname ?: ""
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

    fun setCloseTimeOfHomeFreePass() {
        viewModelScope.launch {
            setCloseTimeOfHomeFreePassUseCase(System.currentTimeMillis())
        }
    }

    fun showTooltip() {
        viewModelScope.launch {
            issueCouponUseCase(Unit)?.let {
                _isShowTooltip.value = true
            }
        }
    }

    fun hideTooltip() {
        _isShowTooltip.value = false
    }
}
