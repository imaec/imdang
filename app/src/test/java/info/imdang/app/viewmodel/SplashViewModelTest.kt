package info.imdang.app.viewmodel

import info.imdang.app.ui.splash.SplashEvent
import info.imdang.app.ui.splash.SplashViewModel
import info.imdang.domain.model.auth.TokenDto
import info.imdang.domain.usecase.auth.GetTokenUseCase
import info.imdang.domain.usecase.auth.ReissueTokenUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    private lateinit var getTokenUseCase: GetTokenUseCase
    private lateinit var reissueTokenUseCase: ReissueTokenUseCase
    private lateinit var viewModel: SplashViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        getTokenUseCase = mockk()
        reissueTokenUseCase = mockk()

        viewModel = SplashViewModel(
            getTokenUseCase = getTokenUseCase,
            reissueTokenUseCase = reissueTokenUseCase
        )
    }

    @Test
    fun `SplashViewModel 생성 시 로그인 상태이면 MoveMainScreen 이벤트 발행`() =
        runTest {
            // given
            val accessToken = "testAccessToken"
            val tokenDto = TokenDto(
                accessToken = "testAccessToken",
                refreshToken = "testRefreshToken",
                expiresIn = 0L
            )

            coEvery { getTokenUseCase(Unit, any()) } returns accessToken
            coEvery { reissueTokenUseCase(Unit, any()) } returns tokenDto

            // when
            delay(100)

            // then
            assertEquals(SplashEvent.MoveMainScreen, viewModel.event.first())
        }
}
