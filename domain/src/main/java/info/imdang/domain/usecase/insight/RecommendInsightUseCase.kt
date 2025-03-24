package info.imdang.domain.usecase.insight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.repository.fake.FakeInsightRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class RecommendInsightUseCase @Inject constructor(
    private val repository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<RecommendInsightParams, InsightIdDto>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(parameters: RecommendInsightParams): InsightIdDto =
        repository.recommendInsight(
            insightId = parameters.insightId,
            memberId = parameters.memberId
        )
}

data class RecommendInsightParams(
    val insightId: String,
    val memberId: String
)

class FakeRecommendInsightUseCase : RecommendInsightUseCase(
    repository = FakeInsightRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
