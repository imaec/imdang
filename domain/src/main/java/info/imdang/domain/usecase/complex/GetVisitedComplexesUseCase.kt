package info.imdang.domain.usecase.complex

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.complex.VisitedComplexDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.repository.fake.FakeInsightRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class GetVisitedComplexesUseCase @Inject constructor(
    private val repository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, List<VisitedComplexDto>>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit): List<VisitedComplexDto> =
        repository.getVisitedComplexes()
}

class FakeGetVisitedComplexesUseCase : GetVisitedComplexesUseCase(
    repository = FakeInsightRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
