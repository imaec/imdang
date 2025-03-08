package info.imdang.app.ui.list.region

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.model.insight.InsightVo
import info.imdang.app.model.insight.mapper
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.usecase.insight.GetInsightsByAddressParams
import info.imdang.domain.usecase.insight.GetInsightsByAddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SearchByRegionInsightListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getInsightsByAddressUseCase: GetInsightsByAddressUseCase
) : BaseViewModel() {

    private val siGunGu = savedStateHandle.getStateFlow("siGunGu", "")
    private val eupMyeonDong = savedStateHandle.getStateFlow("eupMyeonDong", "")

    val title = combine(siGunGu, eupMyeonDong) { siGunGu, eupMyeonDong ->
        "$siGunGu $eupMyeonDong"
    }.toStateFlow("")

    protected val _insights = MutableStateFlow<PagingData<InsightVo>>(PagingData.empty())
    val insights = _insights.asStateFlow()

    protected val _insightCount = MutableStateFlow(0)
    val insightCount = _insightCount.asStateFlow()

    init {
        fetchInsights()
    }

    private fun fetchInsights() {
        viewModelScope.launch {
            getInsightsByAddressUseCase(
                GetInsightsByAddressParams(
                    siGunGu = siGunGu.value,
                    eupMyeonDong = eupMyeonDong.value,
                    pagingParams = PagingParams(
                        totalCountListener = {
                            _insightCount.value = it
                        }
                    )
                )
            )
                ?.cachedIn(this)
                ?.collect {
                    _insights.value = it.map(InsightDto::mapper)
                }
        }
    }
}
