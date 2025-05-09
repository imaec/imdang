package info.imdang.app.ui.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.domain.usecase.auth.GetTokenUseCase
import info.imdang.domain.usecase.auth.ReissueTokenUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SplashViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<SplashEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            val accessToken = getTokenUseCase(Unit)
            _event.emit(
                if (accessToken.isNullOrBlank()) {
                    SplashEvent.MoveLoginScreen
                } else {
                    reissueTokenUseCase(Unit)?.let {
                        SplashEvent.MoveMainScreen
                    } ?: SplashEvent.MoveLoginScreen
                }
            )
        }
    }
}
