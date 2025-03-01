package info.imdang.app.ui.my

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.domain.usecase.auth.RemoveTokenUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MyViewModel @Inject constructor(
    private val removeTokenUseCase: RemoveTokenUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<MyEvent>()
    val event = _event.asSharedFlow()

    fun logout() {
        viewModelScope.launch {
            removeTokenUseCase(Unit)
            _event.emit(MyEvent.MoveLoginScreen)
        }
    }
}
