package info.imdang.app.ui.main.home.search.region.preview

import androidx.paging.PagingData
import info.imdang.app.model.district.DistrictVo
import info.imdang.app.ui.main.home.search.region.SearchByRegionViewModel
import info.imdang.domain.usecase.district.FakeGetEupMyeonDongUseCase
import info.imdang.domain.usecase.district.FakeGetSiGunGuUseCase

class FakeSearchByRegionViewModel : SearchByRegionViewModel(
    getSiGunGuUseCase = FakeGetSiGunGuUseCase(),
    getEupMyeonDongUseCase = FakeGetEupMyeonDongUseCase()
) {

    init {
        _guList.value = DistrictVo.getSamples(size = 15)
        _dongList.value = PagingData.from(
            mutableListOf<String>().apply {
                repeat((5..15).random()) {
                    add("논현동")
                }
            }
        )
    }
}
