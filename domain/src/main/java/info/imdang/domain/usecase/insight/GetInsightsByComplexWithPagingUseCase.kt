package info.imdang.domain.usecase.insight

import androidx.paging.PagingData
import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.repository.fake.FakeInsightRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetInsightsByComplexWithPagingUseCase @Inject constructor(
    private val repository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<GetInsightsByComplexParams, Flow<PagingData<InsightDto>>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: GetInsightsByComplexParams
    ): Flow<PagingData<InsightDto>> = repository.getInsightsByComplexWithPaging(
        page = parameters.pagingParams.page - 1,
        size = parameters.pagingParams.size,
        direction = parameters.pagingParams.direction,
        properties = parameters.pagingParams.properties,
        complexName = parameters.complexName,
        totalCountListener = parameters.pagingParams.totalCountListener
    )
}

class FakeGetInsightsByComplexWithPagingUseCase : GetInsightsByComplexWithPagingUseCase(
    repository = FakeInsightRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
