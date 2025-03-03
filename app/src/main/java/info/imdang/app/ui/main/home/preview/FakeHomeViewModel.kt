package info.imdang.app.ui.main.home.preview

import info.imdang.app.model.complex.VisitedComplexVo
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
    getInsightsUseCase = FakeGetInsightsUseCase()
) {
    init {
        _visitedComplexes.value = VisitedComplexVo.getSamples(size = 3)
        _insightsByComplex.value = InsightVo.getSamples(size = 3)
        _newInsights.value = InsightVo.getSamples(size = 5)
        _recommendInsights.value = InsightVo.getSamples(size = 10)
    }
}
