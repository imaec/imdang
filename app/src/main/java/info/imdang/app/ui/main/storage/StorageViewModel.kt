package info.imdang.app.ui.main.storage

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.model.insight.InsightVo
import info.imdang.app.model.insight.mapper
import info.imdang.app.model.myinsight.ComplexVo
import info.imdang.app.model.myinsight.MyInsightAddressVo
import info.imdang.app.model.myinsight.mapper
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.model.myinsight.ComplexDto
import info.imdang.domain.usecase.myinsight.GetAddressesUseCase
import info.imdang.domain.usecase.myinsight.GetComplexesByAddressUseCase
import info.imdang.domain.usecase.myinsight.GetMyInsightsByAddressParams
import info.imdang.domain.usecase.myinsight.GetMyInsightsByAddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class StorageViewModel @Inject constructor(
    private val getAddressesUseCase: GetAddressesUseCase,
    private val getComplexesByAddressUseCase: GetComplexesByAddressUseCase,
    private val getMyInsightsByAddressUseCase: GetMyInsightsByAddressUseCase
) : BaseViewModel() {

    private val _selectedInsightAddressPage = MutableStateFlow(0)
    val selectedInsightAddressPage = _selectedInsightAddressPage.asStateFlow()

    protected val _insights = MutableStateFlow<PagingData<InsightVo>>(PagingData.empty())
    val insights = _insights.asStateFlow()

    protected val _insightCount = MutableStateFlow(0)
    val insightCount = _insightCount.asStateFlow()

    protected val _addresses = MutableStateFlow<List<MyInsightAddressVo>>(emptyList())
    val addresses = _addresses.asStateFlow()

    private val selectedAddress = addresses.map { addresses ->
        addresses.firstOrNull { it.isSelected }
    }.toStateFlow(null)

    private val _isSeeOnlyMyInsight = MutableStateFlow(false)
    val isSeeOnlyMyInsight = _isSeeOnlyMyInsight.asStateFlow()

    protected val _complexes = MutableStateFlow<List<ComplexVo>>(emptyList())
    val complexes = _complexes.asStateFlow()

    private val _selectedComplex = MutableStateFlow<ComplexVo?>(null)
    val selectedComplex = _selectedComplex.asStateFlow()

    init {
        fetchAddresses()
    }

    private fun fetchAddresses() {
        viewModelScope.launch {
            _addresses.value = getAddressesUseCase(Unit)?.mapIndexed { index, myInsightAddressDto ->
                myInsightAddressDto.mapper(isSelected = index == 0)
            } ?: emptyList()
            fetchComplexesByAddress()
            fetchInsightsByAddress()
        }
    }

    private fun fetchComplexesByAddress() {
        viewModelScope.launch {
            _complexes.value = getComplexesByAddressUseCase(
                selectedAddress.value?.toAddressDto() ?: return@launch
            )?.map(ComplexDto::mapper) ?: emptyList()
        }
    }

    private fun fetchInsightsByAddress() {
        viewModelScope.launch {
            getMyInsightsByAddressUseCase(
                GetMyInsightsByAddressParams(
                    address = selectedAddress.value?.toAddressDto() ?: return@launch,
                    complexName = selectedComplex.value?.complexName,
                    onlyMine = isSeeOnlyMyInsight.value,
                    pagingParams = PagingParams(
                        totalCountListener = {
                            _insightCount.value = it
                        }
                    )
                )
            )
                ?.cachedIn(this)
                ?.collect {
                    _insights.value = it.map(InsightDto::mapper)
                }
        }
    }

    fun getSelectedDong(): String = selectedAddress.value?.eupMyeonDong ?: ""

    fun selectInsightAddressPage(page: Int) {
        _selectedInsightAddressPage.value = page
        _addresses.value = addresses.value.mapIndexed { index, myInsightAddressVo ->
            myInsightAddressVo.copy(isSelected = index == page)
        }
        _selectedComplex.value = null
        fetchComplexesByAddress()
        fetchInsightsByAddress()
    }

    fun toggleMyInsightOnly() {
        _isSeeOnlyMyInsight.value = !isSeeOnlyMyInsight.value
        fetchInsightsByAddress()
    }

    fun updateSelectedComplex(item: ComplexVo?) {
        _selectedComplex.value = item
        fetchInsightsByAddress()
    }
}
