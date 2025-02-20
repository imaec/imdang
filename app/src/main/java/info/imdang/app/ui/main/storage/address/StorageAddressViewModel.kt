package info.imdang.app.ui.main.storage.address

import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.component.model.SelectionVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StorageAddressViewModel @Inject constructor() : BaseViewModel() {

    private val _addresses = MutableStateFlow<List<SelectionVo>>(emptyList())
    val addresses = _addresses.asStateFlow()

    init {
        _addresses.value = mutableListOf<SelectionVo>().apply {
            repeat(10) {
                add(SelectionVo(name = "서울 강남구 신논현동", isSelected = it == 0))
            }
        }
    }

    fun onClickAddress(selectedIndex: Int) {
        _addresses.value = addresses.value.mapIndexed { index, selectionVo ->
            selectionVo.copy(isSelected = selectedIndex == index)
        }
    }
}
