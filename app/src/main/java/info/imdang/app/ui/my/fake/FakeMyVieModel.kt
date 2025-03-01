package info.imdang.app.ui.my.fake

import info.imdang.app.ui.my.MyViewModel
import info.imdang.domain.usecase.auth.FakeRemoveTokenUseCase

class FakeMyVieModel : MyViewModel(
    removeTokenUseCase = FakeRemoveTokenUseCase()
)
