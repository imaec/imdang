package info.imdang.data.datasource.remote

import info.imdang.data.model.request.auth.JoinRequest
import info.imdang.data.model.request.auth.LoginRequest
import info.imdang.data.model.request.auth.ReissueTokenRequest
import info.imdang.data.model.response.auth.LoginResponse
import info.imdang.data.model.response.auth.TokenResponse

interface AuthRemoteDataSource {

    suspend fun kakaoLogin(loginRequest: LoginRequest): LoginResponse

    suspend fun googleLogin(loginRequest: LoginRequest): LoginResponse

    suspend fun onboardingJoin(joinRequest: JoinRequest)

    suspend fun reissueToken(reissueTokenRequest: ReissueTokenRequest): TokenResponse?
}
