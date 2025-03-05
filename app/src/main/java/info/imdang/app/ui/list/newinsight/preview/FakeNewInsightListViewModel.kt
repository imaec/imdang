package info.imdang.app.ui.list.newinsight.preview

import androidx.paging.PagingData
import info.imdang.app.model.insight.InsightVo
import info.imdang.app.ui.list.newinsight.NewInsightListViewModel
import info.imdang.domain.usecase.insight.FakeGetInsightsByDateWithPagingUseCase

class FakeNewInsightListViewModel : NewInsightListViewModel(
    getInsightsByDateWithPagingUseCase = FakeGetInsightsByDateWithPagingUseCase()
) {

    init {
        _insights.value = PagingData.from(InsightVo.getSamples(33))
        _insightCount.value = 33
    }
}
