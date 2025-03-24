package info.imdang.domain.repository.fake

import androidx.paging.PagingData
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.repository.MyExchangeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class FakeMyExchangeRepositoryImpl @Inject constructor() : MyExchangeRepository {

    override suspend fun getRequestExchanges(
        memberId: String,
        exchangeRequestStatus: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = flow {
        PagingData.empty<InsightDto>()
    }

    override suspend fun getRequestedExchanges(
        memberId: String,
        exchangeRequestStatus: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = flow {
        PagingData.empty<InsightDto>()
    }
}
