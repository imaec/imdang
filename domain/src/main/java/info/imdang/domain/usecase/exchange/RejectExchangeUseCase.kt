package info.imdang.domain.usecase.exchange

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.exchange.ExchangeDto
import info.imdang.domain.repository.ExchangeRepository
import info.imdang.domain.repository.fake.FakeExchangeRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class RejectExchangeUseCase @Inject constructor(
    private val repository: ExchangeRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<ResponseExchangeParams, ExchangeDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(
        parameters: ResponseExchangeParams
    ): ExchangeDto = repository.rejectExchange(
        exchangeRequestId = parameters.exchangeRequestId,
        memberId = parameters.memberId
    )
}

class FakeRejectExchangeUseCase : RejectExchangeUseCase(
    repository = FakeExchangeRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
