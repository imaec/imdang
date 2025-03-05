package info.imdang.app.ui.main.home.preview

import androidx.paging.PagingData
import info.imdang.app.model.complex.VisitedComplexVo
import info.imdang.app.model.coupon.CouponVo
import info.imdang.app.model.insight.InsightVo
import info.imdang.app.ui.main.home.HomeViewModel
import info.imdang.domain.usecase.complex.FakeGetVisitedComplexesUseCase
import info.imdang.domain.usecase.coupon.FakeGetCouponUseCase
import info.imdang.domain.usecase.coupon.FakeIssueCouponUseCase
import info.imdang.domain.usecase.home.FakeGetCloseTimeOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.FakeGetFirstDateOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.FakeSetCloseTimeOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.FakeSetFirstDateOfHomeFreePassUseCase
import info.imdang.domain.usecase.insight.FakeGetInsightsByComplexUseCase
import info.imdang.domain.usecase.insight.FakeGetInsightsByDateUseCase
import info.imdang.domain.usecase.insight.FakeGetInsightsUseCase
import info.imdang.domain.usecase.myexchange.FakeGetRequestExchangesUseCase
import info.imdang.domain.usecase.myexchange.FakeGetRequestedExchangesUseCase
import info.imdang.domain.usecase.mypage.FakeGetMyPageInfoUseCase
import info.imdang.domain.usecase.notification.FakeHasNewNotificationUseCase

class FakeHomeViewModel : HomeViewModel(
    hasNewNotificationUseCase = FakeHasNewNotificationUseCase(),
    getCouponUseCase = FakeGetCouponUseCase(),
    getMyPageInfoUseCase = FakeGetMyPageInfoUseCase(),
    getFirstDateOfHomeFreePassUseCase = FakeGetFirstDateOfHomeFreePassUseCase(),
    setFirstDateOfHomeFreePassUseCase = FakeSetFirstDateOfHomeFreePassUseCase(),
    getCloseTimeOfHomeFreePassUseCase = FakeGetCloseTimeOfHomeFreePassUseCase(),
    setCloseTimeOfHomeFreePassUseCase = FakeSetCloseTimeOfHomeFreePassUseCase(),
    issueCouponUseCase = FakeIssueCouponUseCase(),
    getVisitedComplexesUseCase = FakeGetVisitedComplexesUseCase(),
    getInsightsByComplexUseCase = FakeGetInsightsByComplexUseCase(),
    getInsightsByDateUseCase = FakeGetInsightsByDateUseCase(),
    getInsightsUseCase = FakeGetInsightsUseCase(),
    getRequestExchangesUseCase = FakeGetRequestExchangesUseCase(),
    getRequestedExchangesUseCase = FakeGetRequestedExchangesUseCase()
) {
    init {
        // 탐색
        _visitedComplexes.value = VisitedComplexVo.getSamples(size = 3)
        _insightsByComplex.value = InsightVo.getSamples(size = 3)
        _newInsights.value = InsightVo.getSamples(size = 5)
        _recommendInsights.value = InsightVo.getSamples(size = 10)
        // 교환소
        _coupon.value = CouponVo(couponCount = 2, couponId = 0)
        _requestExchangeInsights.value = PagingData.from(InsightVo.getSamples(4))
        _requestedExchangeInsights.value = PagingData.from(InsightVo.getSamples(4))
        _exchangeInsightCount.value = 4
    }
}
