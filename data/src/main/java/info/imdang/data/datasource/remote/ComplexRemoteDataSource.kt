package info.imdang.data.datasource.remote

import info.imdang.data.model.response.insight.VisitedComplexResponse

interface ComplexRemoteDataSource {

    suspend fun getVisitedComplexes(): List<VisitedComplexResponse>
}
