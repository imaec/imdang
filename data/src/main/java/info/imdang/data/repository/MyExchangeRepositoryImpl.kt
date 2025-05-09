package info.imdang.data.repository

import androidx.paging.PagingData
import info.imdang.data.datasource.remote.MyExchangeRemoteDataSource
import info.imdang.data.pagingsource.getPagingFlow
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.repository.MyExchangeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class MyExchangeRepositoryImpl @Inject constructor(
    private val myExchangeRemoteDataSource: MyExchangeRemoteDataSource
) : MyExchangeRepository {

    override suspend fun getRequestExchanges(
        memberId: String,
        exchangeRequestStatus: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = getPagingFlow(
        initialPage = page ?: 0,
        pageSize = size ?: 20,
        loadData = { currentPage, pageSize ->
            myExchangeRemoteDataSource.getRequestExchanges(
                memberId = memberId,
                exchangeRequestStatus = exchangeRequestStatus,
                page = currentPage,
                size = pageSize,
                direction = direction,
                properties = properties
            ).mapper()
        },
        totalCountListener = totalCountListener
    )

    override suspend fun getRequestedExchanges(
        memberId: String,
        exchangeRequestStatus: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = getPagingFlow(
        initialPage = page ?: 0,
        pageSize = size ?: 2,
        loadData = { currentPage, pageSize ->
            myExchangeRemoteDataSource.getRequestedExchanges(
                memberId = memberId,
                exchangeRequestStatus = exchangeRequestStatus,
                page = currentPage,
                size = pageSize,
                direction = direction,
                properties = properties
            ).mapper()
        },
        totalCountListener = totalCountListener
    )
}
