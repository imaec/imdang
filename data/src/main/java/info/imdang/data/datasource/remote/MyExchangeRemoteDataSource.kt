package info.imdang.data.datasource.remote

import info.imdang.data.model.response.common.PagingResponse
import info.imdang.data.model.response.insight.InsightResponse
import info.imdang.domain.model.insight.InsightDto

interface MyExchangeRemoteDataSource {

    suspend fun getRequestExchanges(
        memberId: String,
        exchangeRequestStatus: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto>

    suspend fun getRequestedExchanges(
        memberId: String,
        exchangeRequestStatus: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<InsightResponse, InsightDto>
}
