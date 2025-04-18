package info.imdang.remote.service

import info.imdang.data.model.request.auth.JoinRequest
import info.imdang.data.model.request.auth.LoginRequest
import info.imdang.data.model.request.auth.ReissueTokenRequest
import info.imdang.data.model.response.auth.LoginResponse
import info.imdang.data.model.response.auth.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

internal interface AuthService {

    /** 카카오 로그인 */
    @POST("auth/kakao")
    suspend fun kakaoLogin(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    /** 구글 로그인 */
    @POST("auth/google")
    suspend fun googleLogin(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    /** 회원가입 */
    @PUT("auth/join")
    suspend fun join(
        @Body joinRequest: JoinRequest
    )

    /** 토큰 재발급 */
    @POST("auth/reissue")
    suspend fun reissueToken(
        @Body reissueTokenRequest: ReissueTokenRequest
    ): TokenResponse
}
