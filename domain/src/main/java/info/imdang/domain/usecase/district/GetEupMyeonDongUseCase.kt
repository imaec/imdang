package info.imdang.domain.usecase.district

import androidx.paging.PagingData
import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.district.DistrictDto
import info.imdang.domain.repository.DistrictRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetEupMyeonDongUseCase @Inject constructor(
    private val repository: DistrictRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<GetEupMyeonDongParams, Flow<PagingData<DistrictDto>>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: GetEupMyeonDongParams
    ): Flow<PagingData<DistrictDto>> = repository.getEupMyeonDong(
        siGunGu = parameters.siGunGu,
        page = parameters.pagingParams.page - 1,
        size = parameters.pagingParams.size,
        totalCountListener = parameters.pagingParams.totalCountListener
    )
}

data class GetEupMyeonDongParams(
    val siGunGu: String,
    val pagingParams: PagingParams
)

class FakeGetEupMyeonDongUseCase : GetEupMyeonDongUseCase(
    repository = object : DistrictRepository {
        override suspend fun getSiGunGu(page: Int?, size: Int?): PagingDto<DistrictDto> {
            TODO("Not yet implemented")
        }

        override suspend fun getEupMyeonDong(
            siGunGu: String,
            page: Int?,
            size: Int?,
            totalCountListener: ((Int) -> Unit)?
        ): Flow<PagingData<DistrictDto>> {
            TODO("Not yet implemented")
        }
    },
    dispatcher = Dispatchers.IO
)
