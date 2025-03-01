package info.imdang.app.ui.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.model.auth.LoginVo
import info.imdang.app.model.auth.mapper
import info.imdang.component.common.snackbar.showSnackbar
import info.imdang.domain.usecase.auth.GoogleLoginUseCase
import info.imdang.domain.usecase.auth.KakaoLoginUseCase
import info.imdang.domain.usecase.google.GetGoogleAccessTokenUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val getGoogleAccessTokenUseCase: GetGoogleAccessTokenUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

    fun kakaoLogin(token: String) {
        viewModelScope.launch {
            kakaoLoginUseCase(
                parameters = token,
                onError = {
                    launch { showSnackbar("KAKAO LOGIN FAILURE : ${it.message}") }
                }
            )?.mapper()?.let {
                handleLoginResult(it)
            }
        }
    }

    fun getGoogleAccessToken(
        authCode: String,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            getGoogleAccessTokenUseCase(
                parameters = authCode,
                onError = {
                    launch { showSnackbar("GOOGLE GET ACCESS TOKEN FAILURE : ${it.message}") }
                }
            )?.let {
                onSuccess(it.accessToken)
            }
        }
    }

    fun googleLogin(token: String) {
        viewModelScope.launch {
            googleLoginUseCase(
                parameters = token,
                onError = {
                    launch { showSnackbar("GOOGLE LOGIN FAILURE : ${it.message}") }
                }
            )?.mapper()?.let {
                handleLoginResult(it)
            }
        }
    }

    private suspend fun handleLoginResult(loginVo: LoginVo) {
        _event.emit(
            if (loginVo.isJoined) {
                LoginEvent.MoveMainScreen
            } else {
                LoginEvent.MoveOnboardingScreen
            }
        )
    }
}
