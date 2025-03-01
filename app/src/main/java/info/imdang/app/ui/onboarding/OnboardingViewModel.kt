package info.imdang.app.ui.onboarding

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.domain.usecase.auth.RemoveTokenUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class OnboardingViewModel @Inject constructor(
    private val removeTokenUseCase: RemoveTokenUseCase
) : BaseViewModel() {

    fun logout() {
        viewModelScope.launch {
            removeTokenUseCase(Unit)
        }
    }
}
