package info.imdang.domain.usecase.mypage

import info.imdang.domain.IoDispatcher
import info.imdang.domain.repository.MyPageRepository
import info.imdang.domain.repository.fake.FakeMyPageRepositoryImpl
import info.imdang.domain.usecase.UseCase
import info.imdang.domain.usecase.auth.FakeGetSocialAccessTokenUseCase
import info.imdang.domain.usecase.auth.GetSocialAccessTokenUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class WithdrawalKakaoUseCase @Inject constructor(
    private val repository: MyPageRepository,
    private val getSocialAccessTokenUseCase: GetSocialAccessTokenUseCase,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit) {
        val accessToken = getSocialAccessTokenUseCase()
        repository.withdrawalKakao(accessToken)
    }
}

class FakeWithdrawalKakaoUseCase : WithdrawalKakaoUseCase(
    repository = FakeMyPageRepositoryImpl(),
    dispatcher = Dispatchers.IO,
    getSocialAccessTokenUseCase = FakeGetSocialAccessTokenUseCase()
)
