package info.imdang.app.ui.list.visitcomplex.preview

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import info.imdang.app.model.complex.VisitedComplexVo
import info.imdang.app.model.insight.InsightVo
import info.imdang.app.ui.list.visitcomplex.VisitComplexInsightListViewModel
import info.imdang.domain.usecase.complex.FakeGetVisitedComplexesUseCase
import info.imdang.domain.usecase.insight.FakeGetInsightsByComplexWithPagingUseCase

class FakeVisitComplexInsightListViewModel : VisitComplexInsightListViewModel(
    savedStateHandle = SavedStateHandle(),
    getVisitedComplexesUseCase = FakeGetVisitedComplexesUseCase(),
    getInsightsByComplexWithPagingUseCase = FakeGetInsightsByComplexWithPagingUseCase()
) {
    init {
        _visitedComplexes.value = VisitedComplexVo.getSamples(size = 3)
        _insightsByComplex.value = PagingData.from(InsightVo.getSamples(size = 33))
        _insightCount.value = 33
    }
}
