package info.imdang.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    internal fun <T> StateFlow<T?>.isCheckVisible() = map {
        if (it is Set<*>) it.isNotEmpty() else it != null
    }.toStateFlow(false)

    internal fun StateFlow<String>.isBlank() = map { it.isBlank() }.toStateFlow(false)

    internal fun StateFlow<String>.isNotBlank() = map { it.isNotBlank() }.toStateFlow(false)

    internal fun <T> Flow<T>.toStateFlow(initialValue: T) = stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue
    )
}
