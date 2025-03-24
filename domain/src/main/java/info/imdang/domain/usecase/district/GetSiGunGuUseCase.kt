package info.imdang.domain.usecase.district

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.district.DistrictDto
import info.imdang.domain.repository.DistrictRepository
import info.imdang.domain.repository.fake.FakeDistrictRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class GetSiGunGuUseCase @Inject constructor(
    private val repository: DistrictRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<PagingParams, PagingDto<DistrictDto>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: PagingParams
    ): PagingDto<DistrictDto> = repository.getSiGunGu(
        page = parameters.page - 1,
        size = parameters.size
    )
}

class FakeGetSiGunGuUseCase : GetSiGunGuUseCase(
    repository = FakeDistrictRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
