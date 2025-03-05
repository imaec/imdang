package info.imdang.domain.usecase.myexchange

import androidx.paging.PagingData
import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.repository.MyExchangeRepository
import info.imdang.domain.usecase.UseCase
import info.imdang.domain.usecase.auth.FakeGetMemberIdUseCase
import info.imdang.domain.usecase.auth.GetMemberIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetRequestExchangesUseCase @Inject constructor(
    private val repository: MyExchangeRepository,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<GetExchangesParams, Flow<PagingData<InsightDto>>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: GetExchangesParams
    ): Flow<PagingData<InsightDto>> = repository.getRequestExchanges(
        memberId = getMemberIdUseCase(),
        exchangeRequestStatus = parameters.exchangeRequestStatus,
        page = parameters.pagingParams.page - 1,
        size = parameters.pagingParams.size,
        direction = parameters.pagingParams.direction,
        properties = parameters.pagingParams.properties,
        totalCountListener = parameters.pagingParams.totalCountListener
    )
}

data class GetExchangesParams(
    val exchangeRequestStatus: String?,
    val pagingParams: PagingParams
)

class FakeGetRequestExchangesUseCase : GetRequestExchangesUseCase(
    repository = object : MyExchangeRepository {
        override suspend fun getRequestExchanges(
            memberId: String,
            exchangeRequestStatus: String?,
            page: Int?,
            size: Int?,
            direction: String?,
            properties: List<String>?,
            totalCountListener: ((Int) -> Unit)?
        ): Flow<PagingData<InsightDto>> {
            TODO("Not yet implemented")
        }

        override suspend fun getRequestedExchanges(
            memberId: String,
            exchangeRequestStatus: String?,
            page: Int?,
            size: Int?,
            direction: String?,
            properties: List<String>?,
            totalCountListener: ((Int) -> Unit)?
        ): Flow<PagingData<InsightDto>> {
            TODO("Not yet implemented")
        }
    },
    getMemberIdUseCase = FakeGetMemberIdUseCase(),
    dispatcher = Dispatchers.IO
)
