package info.imdang.app.ui.join.preview

import info.imdang.app.ui.join.JoinViewModel
import info.imdang.domain.usecase.auth.FakeJoinUseCase
import info.imdang.domain.usecase.auth.FakeRemoveTokenUseCase

class FakeJoinViewModel : JoinViewModel(
    joinUseCase = FakeJoinUseCase(),
    removeTokenUseCase = FakeRemoveTokenUseCase()
)
