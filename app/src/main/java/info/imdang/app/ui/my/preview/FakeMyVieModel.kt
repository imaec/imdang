package info.imdang.app.ui.my.preview

import info.imdang.app.ui.my.MyViewModel
import info.imdang.domain.usecase.auth.FakeRemoveTokenUseCase
import info.imdang.domain.usecase.mypage.FakeGetMyPageInfoUseCase

class FakeMyVieModel : MyViewModel(
    getMyPageInfoUseCase = FakeGetMyPageInfoUseCase(),
    removeTokenUseCase = FakeRemoveTokenUseCase()
)
