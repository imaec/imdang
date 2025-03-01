package info.imdang.app.ui.onboarding.preview

import info.imdang.app.ui.onboarding.OnboardingViewModel
import info.imdang.domain.usecase.auth.FakeRemoveTokenUseCase

class FakeOnboardingViewModel : OnboardingViewModel(
    removeTokenUseCase = FakeRemoveTokenUseCase()
)
