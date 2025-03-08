package info.imdang.app.ui.my.withdraw

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.model.auth.LoginType
import info.imdang.domain.usecase.auth.GetLoginTypeUseCase
import info.imdang.domain.usecase.auth.RemoveTokenUseCase
import info.imdang.domain.usecase.mypage.WithdrawalGoogleUseCase
import info.imdang.domain.usecase.mypage.WithdrawalKakaoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class WithdrawViewModel @Inject constructor(
    getLoginTypeUseCase: GetLoginTypeUseCase,
    private val removeTokenUseCase: RemoveTokenUseCase,
    private val withdrawalKakaoUseCase: WithdrawalKakaoUseCase,
    private val withdrawalGoogleUseCase: WithdrawalGoogleUseCase
) : BaseViewModel() {

    private val loginType = getLoginTypeUseCase()

    private val _event = MutableSharedFlow<WithdrawEvent>()
    val event = _event.asSharedFlow()

    fun withdraw() {
        viewModelScope.launch {
            when (loginType) {
                LoginType.KAKAO.name -> withdrawalKakaoUseCase(Unit)
                LoginType.GOOGLE.name -> withdrawalGoogleUseCase(Unit)
            }
            removeTokenUseCase(Unit)
            _event.emit(WithdrawEvent.MoveLoginScreen)
        }
    }
}
