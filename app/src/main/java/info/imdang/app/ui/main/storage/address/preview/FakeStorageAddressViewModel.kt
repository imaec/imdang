package info.imdang.app.ui.main.storage.address.preview

import androidx.lifecycle.SavedStateHandle
import info.imdang.app.model.myinsight.MyInsightAddressVo
import info.imdang.app.ui.main.storage.address.StorageAddressViewModel
import info.imdang.domain.usecase.myinsight.FakeGetAddressesUseCase

class FakeStorageAddressViewModel : StorageAddressViewModel(
    savedStateHandle = SavedStateHandle(),
    getAddressesUseCase = FakeGetAddressesUseCase()
) {
    init {
        _addresses.value = MyInsightAddressVo.getSamples(size = 4)
    }
}
