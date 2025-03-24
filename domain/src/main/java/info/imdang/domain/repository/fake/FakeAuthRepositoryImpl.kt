package info.imdang.domain.repository.fake

import info.imdang.domain.model.auth.LoginDto
import info.imdang.domain.model.auth.TokenDto
import info.imdang.domain.repository.AuthRepository
import javax.inject.Inject

internal class FakeAuthRepositoryImpl @Inject constructor() : AuthRepository {

    override suspend fun kakaoLogin(authorizationCode: String): LoginDto = LoginDto(
        accessToken = "accessToken",
        refreshToken = "refreshToken",
        expiresIn = 0L,
        memberId = "memberId",
        joined = true
    )

    override suspend fun googleLogin(authorizationCode: String): LoginDto = LoginDto(
        accessToken = "accessToken",
        refreshToken = "refreshToken",
        expiresIn = 0L,
        memberId = "memberId",
        joined = true
    )

    override suspend fun join(
        nickname: String,
        birthDate: String,
        gender: String?,
        deviceToken: String
    ) {
    }

    override suspend fun reissueToken(memberId: String, refreshToken: String): TokenDto? = null

    override suspend fun saveAccessToken(accessToken: String) {
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
    }

    override suspend fun getAccessToken(): String = ""

    override suspend fun getRefreshToken(): String = ""

    override suspend fun removeToken() {
    }

    override fun saveMemberId(memberId: String) {
    }

    override fun getMemberId(): String = ""

    override fun saveLoginType(loginType: String) {
    }

    override fun getLoginType(): String = ""

    override fun saveSocialAccessToken(accessToken: String) {
    }

    override fun getSocialAccessToken(): String = ""
}
