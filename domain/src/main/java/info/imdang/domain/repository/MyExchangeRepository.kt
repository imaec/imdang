package info.imdang.domain.repository

import androidx.paging.PagingData
import info.imdang.domain.model.insight.InsightDto
import kotlinx.coroutines.flow.Flow

interface MyExchangeRepository {

    suspend fun getRequestExchanges(
        memberId: String,
        exchangeRequestStatus: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>>

    suspend fun getRequestedExchanges(
        memberId: String,
        exchangeRequestStatus: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>>
}
