package info.imdang.app.ui.insight.detail.preview

import androidx.lifecycle.SavedStateHandle
import info.imdang.app.model.insight.InsightDetailItem
import info.imdang.app.model.insight.InsightDetailVo
import info.imdang.app.ui.insight.detail.InsightDetailViewModel
import info.imdang.domain.usecase.auth.FakeGetMemberIdUseCase
import info.imdang.domain.usecase.coupon.FakeGetCouponUseCase
import info.imdang.domain.usecase.exchange.FakeAcceptExchangeUseCase
import info.imdang.domain.usecase.exchange.FakeRejectExchangeUseCase
import info.imdang.domain.usecase.exchange.FakeRequestExchangeUseCase
import info.imdang.domain.usecase.insight.FakeGetInsightDetailUseCase
import info.imdang.domain.usecase.insight.FakeRecommendInsightUseCase
import info.imdang.domain.usecase.insight.FakeReportInsightUseCase
import info.imdang.domain.usecase.myinsight.FakeGetMyInsightsUseCase
import info.imdang.domain.usecase.myinsight.FakeGetMyInsightsWithPagingUseCase

class FakeInsightDetailViewModel : InsightDetailViewModel(
    savedStateHandle = SavedStateHandle(),
    getMemberIdUseCase = FakeGetMemberIdUseCase(),
    getInsightDetailUseCase = FakeGetInsightDetailUseCase(),
    getMyInsightsUseCase = FakeGetMyInsightsUseCase(),
    getMyInsightsWithPagingUseCase = FakeGetMyInsightsWithPagingUseCase(),
    getCouponUseCase = FakeGetCouponUseCase(),
    requestExchangeUseCase = FakeRequestExchangeUseCase(),
    acceptExchangeUseCase = FakeAcceptExchangeUseCase(),
    rejectExchangeUseCase = FakeRejectExchangeUseCase(),
    recommendInsightUseCase = FakeRecommendInsightUseCase(),
    reportInsightUseCase = FakeReportInsightUseCase()
) {

    init {
        _insight.value = InsightDetailVo.getSample()
        _insightDetails.value = listOf(
            InsightDetailVo.getSample().toBasicInfo(),
            InsightDetailItem.Infra(InsightDetailVo.getSample().infra!!),
            InsightDetailItem.ComplexEnvironment(InsightDetailVo.getSample().complexEnvironment!!),
            InsightDetailItem.ComplexFacility(InsightDetailVo.getSample().complexFacility!!),
            InsightDetailItem.GoodNews(
                InsightDetailVo.getSample().goodNews!!,
                InsightDetailVo.getSample().insightDetailStatus
            )
        )
    }
}
