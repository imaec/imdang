package info.imdang.app.ui.insight.write.preview

import androidx.lifecycle.SavedStateHandle
import info.imdang.app.ui.insight.write.WriteInsightViewModel
import info.imdang.domain.usecase.insight.FakeGetInsightDetailUseCase
import info.imdang.domain.usecase.insight.FakeUpdateInsightUseCase
import info.imdang.domain.usecase.insight.FakeWriteInsightUseCase

class FakeWriteInsightViewModel : WriteInsightViewModel(
    savedStateHandle = SavedStateHandle(),
    getInsightDetailUseCase = FakeGetInsightDetailUseCase(),
    writeInsightUseCase = FakeWriteInsightUseCase(),
    updateInsightUseCase = FakeUpdateInsightUseCase()
)
