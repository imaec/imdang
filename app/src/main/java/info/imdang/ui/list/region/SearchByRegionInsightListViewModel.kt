package info.imdang.ui.list.region

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class SearchByRegionInsightListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val siGunGu = savedStateHandle.getStateFlow("siGunGu", "")
    private val eupMyeonDong = savedStateHandle.getStateFlow("eupMyeonDong", "")

    val title = combine(siGunGu, eupMyeonDong) { siGunGu, eupMyeonDong ->
        "$siGunGu $eupMyeonDong"
    }.toStateFlow("")

    private val _insights = MutableStateFlow<List<String>>(emptyList())
    val insights = _insights.asStateFlow()

    init {
        fetchInsights()
    }

    private fun fetchInsights() {
        _insights.value = mutableListOf<String>().apply {
            repeat(33) {
                add("초역세권 대단지 아파트 후기")
            }
        }
    }
}
