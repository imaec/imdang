package info.imdang.domain.usecase.exchange

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.exchange.ExchangeDto
import info.imdang.domain.repository.ExchangeRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class RequestExchangeUseCase @Inject constructor(
    private val repository: ExchangeRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<RequestExchangeParams, ExchangeDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(
        parameters: RequestExchangeParams
    ): ExchangeDto = repository.requestExchange(
        insightId = parameters.insightId,
        memberId = parameters.memberId,
        myInsightId = parameters.myInsightId,
        couponId = parameters.couponId
    )
}

data class RequestExchangeParams(
    val insightId: String,
    val memberId: String?,
    val myInsightId: String?,
    val couponId: Long?
)

class FakeRequestExchangeUseCase : RequestExchangeUseCase(
    repository = object : ExchangeRepository {
        override suspend fun requestExchange(
            insightId: String,
            memberId: String?,
            myInsightId: String?,
            couponId: Long?
        ): ExchangeDto {
            TODO("Not yet implemented")
        }

        override suspend fun acceptExchange(
            exchangeRequestId: String,
            memberId: String
        ): ExchangeDto {
            TODO("Not yet implemented")
        }

        override suspend fun rejectExchange(
            exchangeRequestId: String,
            memberId: String
        ): ExchangeDto {
            TODO("Not yet implemented")
        }
    },
    dispatcher = Dispatchers.IO
)
