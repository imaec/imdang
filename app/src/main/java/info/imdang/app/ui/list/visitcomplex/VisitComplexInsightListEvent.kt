package info.imdang.app.ui.list.visitcomplex

import androidx.paging.PagingData
import info.imdang.app.model.insight.InsightVo

sealed class VisitComplexInsightListEvent {

    data class ScrollToSelectedPosition(val position: Int) : VisitComplexInsightListEvent()

    data class UpdateInsights(val insights: PagingData<InsightVo>) : VisitComplexInsightListEvent()
}
