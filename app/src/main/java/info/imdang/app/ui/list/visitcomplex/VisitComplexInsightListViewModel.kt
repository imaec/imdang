package info.imdang.app.ui.list.visitcomplex

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.model.complex.VisitedComplexVo
import info.imdang.app.model.complex.mapper
import info.imdang.app.model.insight.InsightVo
import info.imdang.app.model.insight.mapper
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.usecase.complex.GetVisitedComplexesUseCase
import info.imdang.domain.usecase.insight.GetInsightsByComplexParams
import info.imdang.domain.usecase.insight.GetInsightsByComplexWithPagingUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class VisitComplexInsightListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getVisitedComplexesUseCase: GetVisitedComplexesUseCase,
    private val getInsightsByComplexWithPagingUseCase: GetInsightsByComplexWithPagingUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<VisitComplexInsightListEvent>()
    val event = _event.asSharedFlow()

    private val _selectedIndex = MutableStateFlow(
        savedStateHandle.get<String>("selectedIndex")?.toInt() ?: 0
    )
    val selectedIndex = _selectedIndex.asStateFlow()

    protected val _visitedComplexes = MutableStateFlow<List<VisitedComplexVo>>(emptyList())
    val visitedComplexes = _visitedComplexes.asStateFlow()

    protected val _insightsByComplex = MutableStateFlow<PagingData<InsightVo>>(PagingData.empty())
    val insightsByComplex = _insightsByComplex.asStateFlow()

    protected val _insightCount = MutableStateFlow(0)
    val insightCount = _insightCount.asStateFlow()

    init {
        fetchVisitedAptComplexes()
    }

    private fun fetchVisitedAptComplexes() {
        viewModelScope.launch {
            _visitedComplexes.value =
                getVisitedComplexesUseCase(Unit)?.mapIndexed { index, visitedAptComplexDto ->
                    visitedAptComplexDto.mapper(isSelected = index == selectedIndex.value)
                } ?: emptyList()
            fetchInsightsByAptComplex()
        }
    }

    private fun fetchInsightsByAptComplex() {
        viewModelScope.launch {
            val complexName = visitedComplexes.value.firstOrNull {
                it.isSelected
            }?.complexName ?: return@launch
            getInsightsByComplexWithPagingUseCase(
                GetInsightsByComplexParams(
                    complexName = complexName,
                    pagingParams = PagingParams(
                        totalCountListener = {
                            _insightCount.value = it
                        }
                    )
                )
            )
                ?.cachedIn(this)
                ?.collect {
                    _insightsByComplex.value = it.map(InsightDto::mapper)
                    delay(100)
                    _event.emit(
                        VisitComplexInsightListEvent.ScrollToSelectedPosition(selectedIndex.value)
                    )
                }
        }
    }

    fun onClickVisitedComplex(complexVo: VisitedComplexVo) {
        _visitedComplexes.value = visitedComplexes.value.map {
            it.copy(isSelected = it.complexName == complexVo.complexName)
        }
        _selectedIndex.value = visitedComplexes.value.indexOfFirst { it.isSelected }
        fetchInsightsByAptComplex()
    }
}
