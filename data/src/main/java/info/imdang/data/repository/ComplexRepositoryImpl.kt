package info.imdang.data.repository

import info.imdang.data.datasource.remote.ComplexRemoteDataSource
import info.imdang.data.mapper.mapper
import info.imdang.domain.model.complex.VisitedComplexDto
import info.imdang.domain.repository.ComplexRepository
import javax.inject.Inject

internal class ComplexRepositoryImpl @Inject constructor(
    private val complexRemoteDataSource: ComplexRemoteDataSource
) : ComplexRepository {

    override suspend fun getVisitedComplexes(): List<VisitedComplexDto> =
        complexRemoteDataSource.getVisitedComplexes().mapper()
}
