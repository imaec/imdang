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

open class GetInsightsByComplexUseCase @Inject constructor(
    private val repository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<GetInsightsByComplexParams, PagingDto<InsightDto>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: GetInsightsByComplexParams
    ): PagingDto<InsightDto> = repository.getInsightsByComplex(
        page = parameters.pagingParams.page - 1,
        size = parameters.pagingParams.size,
        direction = parameters.pagingParams.direction,
        properties = parameters.pagingParams.properties,
        complexName = parameters.complexName
    )
}

data class GetInsightsByComplexParams(
    val complexName: String,
    val pagingParams: PagingParams
)

class FakeGetInsightsByComplexUseCase : GetInsightsByComplexUseCase(
    repository = FakeInsightRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
