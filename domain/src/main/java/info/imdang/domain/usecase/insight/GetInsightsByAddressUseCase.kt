package info.imdang.domain.usecase.insight

import androidx.paging.PagingData
import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.repository.fake.FakeInsightRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetInsightsByAddressUseCase @Inject constructor(
    private val repository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<GetInsightsByAddressParams, Flow<PagingData<InsightDto>>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: GetInsightsByAddressParams
    ): Flow<PagingData<InsightDto>> = repository.getInsightsByAddress(
        siGunGu = parameters.siGunGu,
        eupMyeonDong = parameters.eupMyeonDong,
        page = parameters.pagingParams.page - 1,
        size = parameters.pagingParams.size,
        direction = parameters.pagingParams.direction,
        properties = parameters.pagingParams.properties,
        totalCountListener = parameters.pagingParams.totalCountListener
    )
}

data class GetInsightsByAddressParams(
    val siGunGu: String,
    val eupMyeonDong: String,
    val pagingParams: PagingParams
)

class FakeGetInsightsByAddressUseCase : GetInsightsByAddressUseCase(
    repository = FakeInsightRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
