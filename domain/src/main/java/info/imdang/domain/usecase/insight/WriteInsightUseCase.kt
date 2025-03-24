package info.imdang.domain.usecase.insight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.model.insight.request.WriteInsightDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.repository.fake.FakeInsightRepositoryImpl
import info.imdang.domain.usecase.UseCase
import java.io.File
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class WriteInsightUseCase @Inject constructor(
    private val repository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<WriteInsightParams, InsightIdDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: WriteInsightParams): InsightIdDto =
        repository.writeInsight(
            parameters.writeInsightDto,
            parameters.mainImage
        )
}

data class WriteInsightParams(
    val writeInsightDto: WriteInsightDto,
    val mainImage: File
)

class FakeWriteInsightUseCase : WriteInsightUseCase(
    repository = FakeInsightRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
