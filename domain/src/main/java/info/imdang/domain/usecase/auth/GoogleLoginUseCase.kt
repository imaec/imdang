package info.imdang.domain.usecase.auth

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.auth.LoginDto
import info.imdang.domain.repository.AuthRepository
import info.imdang.domain.repository.fake.FakeAuthRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class GoogleLoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, LoginDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: String): LoginDto {
        val loginDto = repository.googleLogin(parameters)
        repository.saveAccessToken(loginDto.accessToken)
        repository.saveRefreshToken(loginDto.refreshToken)
        repository.saveMemberId(loginDto.memberId)
        repository.saveLoginType("GOOGLE")
        repository.saveSocialAccessToken(parameters)
        return loginDto
    }
}

class FakeGoogleLoginUseCase : GoogleLoginUseCase(
    repository = FakeAuthRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
