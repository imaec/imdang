package info.imdang.app.ui.main.storage

import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor() : BaseViewModel() {

    private val _complexes = MutableStateFlow<List<String>>(emptyList())
    val complexes = _complexes.asStateFlow()

    private val _selectedComplex = MutableStateFlow<String?>(null)
    val selectedComplex = _selectedComplex.asStateFlow()

    init {
        _complexes.value = mutableListOf<String>().apply {
            repeat(15) {
                add("신논현 더 센트럴 푸르지오 $it")
            }
        }
    }

    fun updateSelectedComplex(complex: String?) {
        _selectedComplex.value = complex
    }
}
