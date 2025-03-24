package info.imdang.domain.repository.fake

import info.imdang.domain.model.complex.VisitedComplexDto
import info.imdang.domain.repository.ComplexRepository
import javax.inject.Inject

internal class FakeComplexRepositoryImpl @Inject constructor() : ComplexRepository {

    override suspend fun getVisitedComplexes(): List<VisitedComplexDto> = emptyList()
}
