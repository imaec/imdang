package info.imdang.domain.repository

import info.imdang.domain.model.complex.VisitedComplexDto

interface ComplexRepository {

    suspend fun getVisitedComplexes(): List<VisitedComplexDto>
}
