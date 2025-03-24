package info.imdang.app.viewmodel

import info.imdang.app.ui.login.LoginEvent
import info.imdang.app.ui.login.LoginViewModel
import info.imdang.domain.model.auth.LoginDto
import info.imdang.domain.model.google.GoogleAccessTokenDto
import info.imdang.domain.usecase.auth.GoogleLoginUseCase
import info.imdang.domain.usecase.auth.KakaoLoginUseCase
import info.imdang.domain.usecase.google.GetGoogleAccessTokenUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var kakaoLoginUseCase: KakaoLoginUseCase
    private lateinit var googleLoginUseCase: GoogleLoginUseCase
    private lateinit var getGoogleAccessTokenUseCase: GetGoogleAccessTokenUseCase
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        kakaoLoginUseCase = mockk()
        googleLoginUseCase = mockk()
        getGoogleAccessTokenUseCase = mockk()
        viewModel = LoginViewModel(
            kakaoLoginUseCase = kakaoLoginUseCase,
            googleLoginUseCase = googleLoginUseCase,
            getGoogleAccessTokenUseCase = getGoogleAccessTokenUseCase
        )
    }

    @Test
    fun `회원가입을 하지 않은 사용자가 kakaoLogin 성공 시 MoveOnboardingScreen 이벤트 발행`() = runTest {
        // given
        val token = "test_kakao_token"
        val loginDto = LoginDto(
            accessToken = "accessToken",
            refreshToken = "refreshToken",
            expiresIn = 0L,
            memberId = "memberId",
            joined = false
        )

        coEvery { kakaoLoginUseCase(any(), any()) } returns loginDto

        // when
        viewModel.kakaoLogin(token)

        // then
        assertEquals(LoginEvent.MoveOnboardingScreen, viewModel.event.first())
    }

    @Test
    fun `회원가입을 완료한 사용자가 kakaoLogin 성공 시 MoveMainScreen 이벤트 발행`() = runTest {
        // given
        val token = "test_kakao_token"
        val loginDto = LoginDto(
            accessToken = "accessToken",
            refreshToken = "refreshToken",
            expiresIn = 0L,
            memberId = "memberId",
            joined = true
        )

        coEvery { kakaoLoginUseCase(any(), any()) } returns loginDto

        // when
        viewModel.kakaoLogin(token)

        // then
        assertEquals(LoginEvent.MoveMainScreen, viewModel.event.first())
    }

    @Test
    fun `회원가입을 하지 않은 사용자가 googleLogin 성공 시 MoveOnboardingScreen 이벤트 발행`() = runTest {
        // given
        val token = "test_google_token"
        val loginDto = LoginDto(
            accessToken = "accessToken",
            refreshToken = "refreshToken",
            expiresIn = 0L,
            memberId = "memberId",
            joined = false
        )

        coEvery { googleLoginUseCase(any(), any()) } returns loginDto

        // when
        viewModel.googleLogin(token)

        // then
        assertEquals(LoginEvent.MoveOnboardingScreen, viewModel.event.first())
    }

    @Test
    fun `getGoogleAccessToken 성공 시 onSuccess 콜백 호출`() = runTest {
        // given
        val authCode = "auth_code"
        val accessToken = "google_access_token"
        val googleAccessTokenDto = GoogleAccessTokenDto(
            accessToken = accessToken,
            expiresIn = 0,
            scope = "",
            tokenType = false,
            idToken = ""
        )

        coEvery { getGoogleAccessTokenUseCase(any(), any()) } returns googleAccessTokenDto

        val deferred = CompletableDeferred<String>()

        // when
        viewModel.getGoogleAccessToken(
            authCode = authCode,
            onSuccess = {
                deferred.complete(it)
            }
        )

        // then
        assertEquals(accessToken, deferred.await())
    }

    @Test
    fun `회원가입을 완료한 사용자가 googleLogin 성공 시 MoveMainScreen 이벤트 발행`() = runTest {
        // given
        val token = "test_google_token"
        val loginDto = LoginDto(
            accessToken = "accessToken",
            refreshToken = "refreshToken",
            expiresIn = 0L,
            memberId = "memberId",
            joined = true
        )

        coEvery { googleLoginUseCase(any(), any()) } returns loginDto

        // when
        viewModel.googleLogin(token)

        // then
        assertEquals(LoginEvent.MoveMainScreen, viewModel.event.first())
    }
}
