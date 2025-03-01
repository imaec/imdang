package info.imdang.app.ui.login.preview

import info.imdang.app.ui.login.LoginViewModel
import info.imdang.domain.usecase.auth.FakeGoogleLoginUseCase
import info.imdang.domain.usecase.auth.FakeKakaoLoginUseCase
import info.imdang.domain.usecase.google.FakeGetGoogleAccessTokenUseCase

open class FakeLoginViewModel : LoginViewModel(
    kakaoLoginUseCase = FakeKakaoLoginUseCase(),
    googleLoginUseCase = FakeGoogleLoginUseCase(),
    getGoogleAccessTokenUseCase = FakeGetGoogleAccessTokenUseCase()
)
