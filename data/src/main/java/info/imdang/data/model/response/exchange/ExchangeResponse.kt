package info.imdang.data.model.response.exchange

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.exchange.ExchangeDto

data class ExchangeResponse(
    val exchangeRequestId: String
) : DataToDomainMapper<ExchangeDto> {
    override fun mapper(): ExchangeDto = ExchangeDto(
        exchangeRequestId = exchangeRequestId
    )
}
