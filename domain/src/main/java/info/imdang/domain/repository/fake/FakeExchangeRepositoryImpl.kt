package info.imdang.domain.repository.fake

import info.imdang.domain.model.exchange.ExchangeDto
import info.imdang.domain.repository.ExchangeRepository
import javax.inject.Inject

internal class FakeExchangeRepositoryImpl @Inject constructor() : ExchangeRepository {

    override suspend fun requestExchange(
        insightId: String,
        memberId: String?,
        myInsightId: String?,
        couponId: Long?
    ): ExchangeDto = ExchangeDto(exchangeRequestId = "")

    override suspend fun acceptExchange(
        exchangeRequestId: String,
        memberId: String
    ): ExchangeDto = ExchangeDto(exchangeRequestId = "")

    override suspend fun rejectExchange(
        exchangeRequestId: String,
        memberId: String
    ): ExchangeDto = ExchangeDto(exchangeRequestId = "")
}
