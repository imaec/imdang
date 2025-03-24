package info.imdang.domain.usecase.insight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.repository.fake.FakeInsightRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class ReportInsightUseCase @Inject constructor(
    private val repository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<ReportInsightParams, InsightIdDto>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(parameters: ReportInsightParams): InsightIdDto =
        repository.reportInsight(
            insightId = parameters.insightId,
            memberId = parameters.memberId
        )
}

data class ReportInsightParams(
    val insightId: String,
    val memberId: String
)

class FakeReportInsightUseCase : ReportInsightUseCase(
    repository = FakeInsightRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
