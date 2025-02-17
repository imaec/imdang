package info.imdang.ui.main.home.search.region

import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.base.BaseViewModel
import info.imdang.component.model.SelectionVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchByRegionViewModel @Inject constructor() : BaseViewModel() {

    private val _guList = MutableStateFlow<List<SelectionVo>>(emptyList())
    val guList = _guList.asStateFlow()

    private val _dongList = MutableStateFlow<List<String>>(emptyList())
    val dongList = _dongList.asStateFlow()

    init {
        fetchSiGunGu()
    }

    private fun fetchSiGunGu() {
        _guList.value = mutableListOf<SelectionVo>().apply {
            repeat(15) {
                add(SelectionVo("강남구"))
            }
        }
        selectGu(0)
    }

    fun selectGu(selectedIndex: Int) {
        _guList.value = guList.value.mapIndexed { index, regionVo ->
            regionVo.copy(isSelected = index == selectedIndex)
        }
        fetchEupMyeonDong()
    }

    private fun fetchEupMyeonDong() {
        _dongList.value = mutableListOf<String>().apply {
            repeat((5..15).random()) {
                add("논현동")
            }
        }
    }

    fun getSelectedGu(): String? = guList.value.firstOrNull { it.isSelected }?.name
}
