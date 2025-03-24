package info.imdang.domain.usecase.insight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.repository.fake.FakeInsightRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class GetInsightsUseCase @Inject constructor(
    private val repository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<PagingParams, PagingDto<InsightDto>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: PagingParams
    ): PagingDto<InsightDto> = repository.getInsights(
        page = parameters.page - 1,
        size = parameters.size,
        direction = parameters.direction,
        properties = parameters.properties
    )
}

class FakeGetInsightsUseCase : GetInsightsUseCase(
    repository = FakeInsightRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
