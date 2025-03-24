package info.imdang.domain.usecase.myinsight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.repository.MyInsightRepository
import info.imdang.domain.repository.fake.FakeMyInsightRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class GetMyInsightsUseCase @Inject constructor(
    private val repository: MyInsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<PagingParams, PagingDto<InsightDto>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: PagingParams
    ): PagingDto<InsightDto> = repository.getMyInsights(
        page = parameters.page - 1,
        size = parameters.size,
        direction = parameters.direction,
        properties = parameters.properties
    )
}

class FakeGetMyInsightsUseCase : GetMyInsightsUseCase(
    repository = FakeMyInsightRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
