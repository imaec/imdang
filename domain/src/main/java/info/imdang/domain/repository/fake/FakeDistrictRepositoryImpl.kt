package info.imdang.domain.repository.fake

import androidx.paging.PagingData
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.district.DistrictDto
import info.imdang.domain.repository.DistrictRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class FakeDistrictRepositoryImpl @Inject constructor() : DistrictRepository {

    override suspend fun getSiGunGu(
        page: Int?,
        size: Int?
    ): PagingDto<DistrictDto> = PagingDto.empty()

    override suspend fun getEupMyeonDong(
        siGunGu: String,
        page: Int?,
        size: Int?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<DistrictDto>> = flow {
        PagingData.empty<DistrictDto>()
    }
}
