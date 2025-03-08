package info.imdang.app.model.my

import info.imdang.domain.model.mypage.MyPageDto

data class MyPageVo(
    val nickname: String,
    val insightCount: Int,
    val exchangeCount: Int
)

fun MyPageDto.mapper(): MyPageVo = MyPageVo(
    nickname = nickname,
    insightCount = insightCount,
    exchangeCount = requestCount
)
