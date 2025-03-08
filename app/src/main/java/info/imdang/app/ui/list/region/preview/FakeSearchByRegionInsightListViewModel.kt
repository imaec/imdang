package info.imdang.app.ui.list.region.preview

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import info.imdang.app.model.insight.InsightVo
import info.imdang.app.ui.list.region.SearchByRegionInsightListViewModel
import info.imdang.domain.usecase.insight.FakeGetInsightsByAddressUseCase

class FakeSearchByRegionInsightListViewModel : SearchByRegionInsightListViewModel(
    savedStateHandle = SavedStateHandle(),
    getInsightsByAddressUseCase = FakeGetInsightsByAddressUseCase()
) {

    init {
        _insights.value = PagingData.from(InsightVo.getSamples(size = 33))
        _insightCount.value = 33
    }
}
