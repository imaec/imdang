package info.imdang.app.ui.list.newinsight

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
import info.imdang.domain.usecase.insight.GetInsightsByDateParams
import info.imdang.domain.usecase.insight.GetInsightsByDateWithPagingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class NewInsightListViewModel @Inject constructor(
    private val getInsightsByDateWithPagingUseCase: GetInsightsByDateWithPagingUseCase
) : BaseViewModel() {

    protected val _insights = MutableStateFlow<PagingData<InsightVo>>(PagingData.empty())
    val insights = _insights.asStateFlow()

    protected val _insightCount = MutableStateFlow(0)
    val insightCount = _insightCount.asStateFlow()

    init {
        fetchInsights()
    }

    private fun fetchInsights() {
        viewModelScope.launch {
            getInsightsByDateWithPagingUseCase(
                GetInsightsByDateParams(
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
