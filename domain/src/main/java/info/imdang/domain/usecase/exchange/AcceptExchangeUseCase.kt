package info.imdang.domain.usecase.exchange

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.exchange.ExchangeDto
import info.imdang.domain.repository.ExchangeRepository
import info.imdang.domain.repository.fake.FakeExchangeRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class AcceptExchangeUseCase @Inject constructor(
    private val repository: ExchangeRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<ResponseExchangeParams, ExchangeDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(
        parameters: ResponseExchangeParams
    ): ExchangeDto = repository.acceptExchange(
        exchangeRequestId = parameters.exchangeRequestId,
        memberId = parameters.memberId
    )
}

data class ResponseExchangeParams(
    val exchangeRequestId: String,
    val memberId: String
)

class FakeAcceptExchangeUseCase : AcceptExchangeUseCase(
    repository = FakeExchangeRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
