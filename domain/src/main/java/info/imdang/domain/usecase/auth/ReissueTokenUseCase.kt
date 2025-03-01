package info.imdang.domain.usecase.auth

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.auth.LoginDto
import info.imdang.domain.model.auth.TokenDto
import info.imdang.domain.repository.AuthRepository
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
    repository = object : AuthRepository {
        override suspend fun kakaoLogin(authorizationCode: String): LoginDto {
            TODO("Not yet implemented")
        }

        override suspend fun googleLogin(authorizationCode: String): LoginDto {
            TODO("Not yet implemented")
        }

        override suspend fun join(
            nickname: String,
            birthDate: String,
            gender: String?,
            deviceToken: String
        ) {
            TODO("Not yet implemented")
        }

        override suspend fun reissueToken(memberId: String, refreshToken: String): TokenDto? {
            TODO("Not yet implemented")
        }

        override suspend fun saveAccessToken(accessToken: String) {
            TODO("Not yet implemented")
        }

        override suspend fun saveRefreshToken(refreshToken: String) {
            TODO("Not yet implemented")
        }

        override suspend fun getAccessToken(): String {
            TODO("Not yet implemented")
        }

        override suspend fun getRefreshToken(): String {
            TODO("Not yet implemented")
        }

        override suspend fun removeToken() {
            TODO("Not yet implemented")
        }

        override fun saveMemberId(memberId: String) {
            TODO("Not yet implemented")
        }

        override fun getMemberId(): String {
            TODO("Not yet implemented")
        }

        override fun saveLoginType(loginType: String) {
            TODO("Not yet implemented")
        }

        override fun getLoginType(): String {
            TODO("Not yet implemented")
        }

        override fun saveOriginAccessToken(accessToken: String) {
            TODO("Not yet implemented")
        }

        override fun getOriginAccessToken(): String {
            TODO("Not yet implemented")
        }
    },
    dispatcher = Dispatchers.IO
)
