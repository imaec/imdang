package info.imdang.app.ui.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.model.auth.mapper
import info.imdang.component.common.snackbar.showSnackbar
import info.imdang.domain.usecase.auth.KakaoLoginUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

    fun kakaoLogin(token: String) {
        viewModelScope.launch {
            kakaoLoginUseCase(
                parameters = token,
                onError = {
                    launch {
                        showSnackbar("KAKAO LOGIN FAILURE : ${it.message}")
                    }
                }
            )?.mapper()?.let {
                _event.emit(
                    if (it.isJoined) {
                        LoginEvent.MoveMainScreen
                    } else {
                        LoginEvent.MoveOnboardingScreen
                    }
                )
            }
        }
    }
}
