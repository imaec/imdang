package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.ComplexRemoteDataSource
import info.imdang.data.model.response.insight.VisitedComplexResponse
import info.imdang.remote.service.ComplexService
import javax.inject.Inject

internal class ComplexRemoteDataSourceImpl @Inject constructor(
    private val complexService: ComplexService
) : ComplexRemoteDataSource {

    override suspend fun getVisitedComplexes(): List<VisitedComplexResponse> =
        complexService.getVisitedComplexes()
}
