package info.imdang.domain.usecase.auth

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.auth.TokenDto
import info.imdang.domain.repository.AuthRepository
import info.imdang.domain.repository.fake.FakeAuthRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class ReissueTokenUseCase @Inject constructor(
    private val repository: AuthRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, TokenDto?>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit): TokenDto? {
        val tokenDto = repository.reissueToken(
            memberId = repository.getMemberId(),
            refreshToken = repository.getRefreshToken()
        )
        tokenDto?.let {
            repository.saveAccessToken(tokenDto.accessToken)
            repository.saveRefreshToken(tokenDto.refreshToken)
        }
        return tokenDto
    }
}

class FakeReissueTokenUseCase : ReissueTokenUseCase(
    repository = FakeAuthRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
