package info.imdang.domain.repository.fake

import info.imdang.domain.model.mypage.MyPageDto
import info.imdang.domain.repository.MyPageRepository
import javax.inject.Inject

class FakeMyPageRepositoryImpl @Inject constructor() : MyPageRepository {

    override suspend fun getMyPageInfo(): MyPageDto = MyPageDto(
        nickname = "",
        insightCount = 0,
        requestCount = 0
    )

    override suspend fun withdrawalKakao(accessToken: String) {
    }

    override suspend fun withdrawalGoogle(accessToken: String) {
    }
}
