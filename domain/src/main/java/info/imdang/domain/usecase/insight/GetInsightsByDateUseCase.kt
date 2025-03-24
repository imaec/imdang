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

open class GetInsightsByDateUseCase @Inject constructor(
    private val repository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<GetInsightsByDateParams, PagingDto<InsightDto>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: GetInsightsByDateParams
    ): PagingDto<InsightDto> = repository.getInsightsByDate(
        date = parameters.date,
        page = parameters.pagingParams.page - 1,
        size = parameters.pagingParams.size,
        direction = parameters.pagingParams.direction,
        properties = parameters.pagingParams.properties,
        totalCountListener = parameters.pagingParams.totalCountListener
    )
}

data class GetInsightsByDateParams(
    val date: String? = null,
    val pagingParams: PagingParams
)

class FakeGetInsightsByDateUseCase : GetInsightsByDateUseCase(
    repository = FakeInsightRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
