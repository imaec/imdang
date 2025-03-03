package info.imdang.app.ui.main.home.preview

import info.imdang.app.ui.main.home.HomeViewModel
import info.imdang.domain.usecase.coupon.FakeGetCouponUseCase
import info.imdang.domain.usecase.coupon.FakeIssueCouponUseCase
import info.imdang.domain.usecase.home.FakeGetCloseTimeOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.FakeGetFirstDateOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.FakeSetCloseTimeOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.FakeSetFirstDateOfHomeFreePassUseCase
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
    issueCouponUseCase = FakeIssueCouponUseCase()
)
