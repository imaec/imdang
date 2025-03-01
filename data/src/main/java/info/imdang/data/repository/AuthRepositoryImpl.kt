package info.imdang.data.repository

import info.imdang.data.datasource.local.AuthLocalDataSource
import info.imdang.data.datasource.remote.AuthRemoteDataSource
import info.imdang.data.model.request.auth.JoinRequest
import info.imdang.data.model.request.auth.LoginRequest
import info.imdang.data.model.request.auth.ReissueTokenRequest
import info.imdang.domain.model.auth.LoginDto
import info.imdang.domain.model.auth.TokenDto
import info.imdang.domain.repository.AuthRepository
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {

    override suspend fun kakaoLogin(authorizationCode: String): LoginDto =
        authRemoteDataSource.kakaoLogin(
            LoginRequest(accessToken = authorizationCode)
        ).mapper()

    override suspend fun googleLogin(authorizationCode: String): LoginDto =
        authRemoteDataSource.googleLogin(
            LoginRequest(accessToken = authorizationCode)
        ).mapper()

    override suspend fun join(
        nickname: String,
        birthDate: String,
        gender: String?,
        deviceToken: String
    ) = authRemoteDataSource.onboardingJoin(
        JoinRequest(
            nickname = nickname,
            birthDate = birthDate,
            gender = gender,
            deviceToken = deviceToken
        )
    )

    override suspend fun reissueToken(memberId: String, refreshToken: String): TokenDto? =
        authRemoteDataSource.reissueToken(
            ReissueTokenRequest(
                memberId = memberId,
                refreshToken = refreshToken
            )
        )?.mapper()

    override suspend fun saveAccessToken(accessToken: String) {
        authLocalDataSource.saveAccessToken(accessToken)
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        authLocalDataSource.saveRefreshToken(refreshToken)
    }

    override suspend fun getAccessToken(): String = authLocalDataSource.getAccessToken()

    override suspend fun getRefreshToken(): String = authLocalDataSource.getRefreshToken()

    override suspend fun removeToken() = authLocalDataSource.removeToken()

    override fun saveMemberId(memberId: String) {
        authLocalDataSource.saveMemberId(memberId)
    }

    override fun getMemberId(): String = authLocalDataSource.getMemberId()

    override fun saveLoginType(loginType: String) {
        authLocalDataSource.saveLoginType(loginType)
    }

    override fun getLoginType(): String = authLocalDataSource.getLoginType()

    override fun saveOriginAccessToken(accessToken: String) {
        authLocalDataSource.saveSocialAccessToken(accessToken)
    }

    override fun getOriginAccessToken(): String = authLocalDataSource.getSocialAccessToken()
}
