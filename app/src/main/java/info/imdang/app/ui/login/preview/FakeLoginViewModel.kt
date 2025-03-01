package info.imdang.app.ui.login.preview

import info.imdang.app.ui.login.LoginViewModel
import info.imdang.domain.usecase.auth.FakeKakaoLoginUseCase

open class FakeLoginViewModel : LoginViewModel(
    kakaoLoginUseCase = FakeKakaoLoginUseCase()
)
