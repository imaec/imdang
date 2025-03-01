package info.imdang.app.ui.splash.preview

import info.imdang.app.ui.splash.SplashViewModel
import info.imdang.domain.usecase.auth.FakeGetTokenUseCase
import info.imdang.domain.usecase.auth.FakeReissueTokenUseCase
import javax.inject.Inject

class FakeSplashViewModel @Inject constructor() : SplashViewModel(
    getTokenUseCase = FakeGetTokenUseCase(),
    reissueTokenUseCase = FakeReissueTokenUseCase()
)
