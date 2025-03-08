package info.imdang.app.ui.my

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.model.my.MyPageVo
import info.imdang.app.model.my.mapper
import info.imdang.domain.usecase.auth.RemoveTokenUseCase
import info.imdang.domain.usecase.mypage.GetMyPageInfoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MyViewModel @Inject constructor(
    private val getMyPageInfoUseCase: GetMyPageInfoUseCase,
    private val removeTokenUseCase: RemoveTokenUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<MyEvent>()
    val event = _event.asSharedFlow()

    private val _myPage = MutableStateFlow<MyPageVo?>(null)
    val myPage = _myPage.asStateFlow()

    init {
        fetchMyPageInfo()
    }

    private fun fetchMyPageInfo() {
        viewModelScope.launch {
            _myPage.value = getMyPageInfoUseCase(Unit)?.mapper()
        }
    }

    fun logout() {
        viewModelScope.launch {
            removeTokenUseCase(Unit)
            _event.emit(MyEvent.MoveLoginScreen)
        }
    }
}
