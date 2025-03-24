package info.imdang.domain.usecase.insight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.insight.InsightDetailDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.repository.fake.FakeInsightRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class GetInsightDetailUseCase @Inject constructor(
    private val repository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, InsightDetailDto>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(parameters: String): InsightDetailDto =
        repository.getInsightDetail(parameters)
}

class FakeGetInsightDetailUseCase : GetInsightDetailUseCase(
    repository = FakeInsightRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
