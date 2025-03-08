package info.imdang.app.ui.main.home.search.region

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.model.district.DistrictVo
import info.imdang.app.model.district.mapper
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.district.DistrictDto
import info.imdang.domain.usecase.district.GetEupMyeonDongParams
import info.imdang.domain.usecase.district.GetEupMyeonDongUseCase
import info.imdang.domain.usecase.district.GetSiGunGuUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SearchByRegionViewModel @Inject constructor(
    private val getSiGunGuUseCase: GetSiGunGuUseCase,
    private val getEupMyeonDongUseCase: GetEupMyeonDongUseCase
) : BaseViewModel() {

    protected val _guList = MutableStateFlow<List<DistrictVo>>(emptyList())
    val guList = _guList.asStateFlow()

    protected val _dongList = MutableStateFlow<PagingData<String>>(PagingData.empty())
    val dongList = _dongList.asStateFlow()

    init {
        fetchSiGunGu()
    }

    private fun fetchSiGunGu() {
        viewModelScope.launch {
            _guList.value = getSiGunGuUseCase(parameters = PagingParams(size = 30))
                ?.content
                ?.map(DistrictDto::mapper)
                ?.sortedBy { it.siGunGu } ?: emptyList()
            selectGu(0)
        }
    }

    fun selectGu(selectedIndex: Int) {
        _guList.value = guList.value.mapIndexed { index, regionVo ->
            regionVo.copy(isSelected = index == selectedIndex)
        }
        fetchEupMyeonDong()
    }

    private fun fetchEupMyeonDong() {
        viewModelScope.launch {
            getEupMyeonDongUseCase(
                parameters = GetEupMyeonDongParams(
                    siGunGu = getSelectedGu() ?: return@launch,
                    pagingParams = PagingParams(size = 30)
                )
            )
                ?.cachedIn(this)
                ?.collect {
                    _dongList.value = it.map {
                        it.mapper().eupMyeonDong ?: ""
                    }
                }
        }
    }

    fun getSelectedGu(): String? = guList.value.firstOrNull { it.isSelected }?.siGunGu
}
