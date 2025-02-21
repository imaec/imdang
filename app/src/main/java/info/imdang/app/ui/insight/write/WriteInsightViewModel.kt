package info.imdang.app.ui.insight.write

import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WriteInsightViewModel @Inject constructor() : BaseViewModel() {

    private val _selectedPage = MutableStateFlow(0)
    val selectedPage = _selectedPage.asStateFlow()

    fun updateSelectedPage(page: Int) {
        _selectedPage.value = page
    }
}
