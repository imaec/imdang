package info.imdang.app.ui.main.storage.address

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.model.myinsight.MyInsightAddressVo
import info.imdang.app.model.myinsight.mapper
import info.imdang.domain.usecase.myinsight.GetAddressesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class StorageAddressViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAddressesUseCase: GetAddressesUseCase
) : BaseViewModel() {

    private val _selectedPage = MutableStateFlow(
        savedStateHandle.get<String>("selectedPage")?.toInt() ?: 0
    )
    val selectedPage = _selectedPage.asStateFlow()

    protected val _addresses = MutableStateFlow<List<MyInsightAddressVo>>(emptyList())
    val addresses = _addresses.asStateFlow()

    init {
        fetchAddresses()
    }

    private fun fetchAddresses() {
        viewModelScope.launch {
            _addresses.value = getAddressesUseCase(Unit)
                ?.mapIndexed { index, myInsightAddressDto ->
                    myInsightAddressDto.mapper(isSelected = index == selectedPage.value)
                } ?: emptyList()
        }
    }

    fun onClickAddress(item: MyInsightAddressVo) {
        _addresses.value = addresses.value.mapIndexed { index, storageAddressVo ->
            if (storageAddressVo.toSiGuDong() == item.toSiGuDong()) {
                _selectedPage.value = index
                storageAddressVo.copy(isSelected = true)
            } else {
                storageAddressVo.copy(isSelected = false)
            }
        }
    }
}
