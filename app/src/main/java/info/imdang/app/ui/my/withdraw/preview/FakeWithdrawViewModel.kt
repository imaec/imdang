package info.imdang.app.ui.my.withdraw.preview

import info.imdang.app.ui.my.withdraw.WithdrawViewModel
import info.imdang.domain.usecase.auth.FakeGetLoginTypeUseCase
import info.imdang.domain.usecase.auth.FakeRemoveTokenUseCase
import info.imdang.domain.usecase.mypage.FakeWithdrawalGoogleUseCase
import info.imdang.domain.usecase.mypage.FakeWithdrawalKakaoUseCase

class FakeWithdrawViewModel : WithdrawViewModel(
    getLoginTypeUseCase = FakeGetLoginTypeUseCase(),
    removeTokenUseCase = FakeRemoveTokenUseCase(),
    withdrawalKakaoUseCase = FakeWithdrawalKakaoUseCase(),
    withdrawalGoogleUseCase = FakeWithdrawalGoogleUseCase()
)
