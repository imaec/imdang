package info.imdang.app.ui.main.storage.preview

import androidx.paging.PagingData
import info.imdang.app.model.insight.InsightVo
import info.imdang.app.model.myinsight.AptComplexVo
import info.imdang.app.model.myinsight.MyInsightAddressVo
import info.imdang.app.ui.main.storage.StorageViewModel
import info.imdang.domain.usecase.myinsight.FakeGetAddressesUseCase
import info.imdang.domain.usecase.myinsight.FakeGetComplexesByAddressUseCase
import info.imdang.domain.usecase.myinsight.FakeGetMyInsightsByAddressUseCase

class FakeStorageViewModel : StorageViewModel(
    getAddressesUseCase = FakeGetAddressesUseCase(),
    getComplexesByAddressUseCase = FakeGetComplexesByAddressUseCase(),
    getMyInsightsByAddressUseCase = FakeGetMyInsightsByAddressUseCase()
) {
    init {
        _insights.value = PagingData.from(InsightVo.getSamples(size = 33))
        _insightCount.value = 33
        _addresses.value = MyInsightAddressVo.getSamples(size = 4)
        _complexes.value = AptComplexVo.getSamples(size = 15)
    }
}
