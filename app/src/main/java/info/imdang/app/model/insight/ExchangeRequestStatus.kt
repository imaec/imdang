package info.imdang.app.model.insight

enum class ExchangeRequestStatus(private val id: Int) {
    PENDING(0), // 내 인사이트인 경우 대기중, 다른 사람의 인사이트인 경우 교환 요청 받은 상태
    REJECTED(1), // 거절, 앱에선 사용하지 않음
    ACCEPTED(2); // 수락, 교환 완료 상태

    companion object {
        fun fromString(status: String?): ExchangeRequestStatus? = entries.firstOrNull {
            it.name == status
        }

        fun fromId(id: Int): ExchangeRequestStatus? = entries.firstOrNull {
            it.id == id
        }
    }
}

fun ExchangeRequestStatus?.toInsightDetailStatus(
    isMyInsight: Boolean,
    isMyExchangeRequest: Boolean
): InsightDetailStatus = when (this) {
    ExchangeRequestStatus.PENDING -> if (isMyExchangeRequest) {
        InsightDetailStatus.EXCHANGE_WAITING
    } else {
        InsightDetailStatus.EXCHANGE_REQUESTED
    }
    ExchangeRequestStatus.ACCEPTED -> InsightDetailStatus.EXCHANGE_COMPLETE
    else -> {
        if (isMyInsight) {
            InsightDetailStatus.MY_INSIGHT
        } else {
            InsightDetailStatus.EXCHANGE_REQUEST
        }
    }
}
