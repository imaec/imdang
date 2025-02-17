package info.imdang.ui.main.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    private val _isShowTooltip = MutableStateFlow(false)
    val isShowTooltip = _isShowTooltip.asStateFlow()

    init {
        showHomeFreePassBottomSheet()
    }

    private fun showHomeFreePassBottomSheet() {
        viewModelScope.launch {
            delay(100)
            _event.emit(HomeEvent.ShowHomeFreePassBottomSheet)
        }
    }

    fun showTooltip() {
        _isShowTooltip.value = true
    }

    fun hideTooltip() {
        _isShowTooltip.value = false
    }
}
