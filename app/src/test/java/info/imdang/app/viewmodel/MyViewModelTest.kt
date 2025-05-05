package info.imdang.app.viewmodel

import info.imdang.app.model.my.mapper
import info.imdang.app.ui.my.MyEvent
import info.imdang.app.ui.my.MyViewModel
import info.imdang.domain.model.mypage.MyPageDto
import info.imdang.domain.usecase.auth.RemoveTokenUseCase
import info.imdang.domain.usecase.mypage.GetMyPageInfoUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MyViewModelTest {

    private lateinit var getMyPageInfoUseCase: GetMyPageInfoUseCase
    private lateinit var removeTokenUseCase: RemoveTokenUseCase
    private lateinit var viewModel: MyViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        getMyPageInfoUseCase = mockk()
        removeTokenUseCase = mockk()

        viewModel = MyViewModel(
            getMyPageInfoUseCase = getMyPageInfoUseCase,
            removeTokenUseCase = removeTokenUseCase
        )
    }

    @Test
    fun `MyViewModel 생성 시 초기 데이터 설정`() = runTest(UnconfinedTestDispatcher()) {
        // given
        val myPageDto = MyPageDto(
            nickname = "testNickname",
            insightCount = 5,
            requestCount = 10
        )

        coEvery { getMyPageInfoUseCase(Unit, any()) } returns myPageDto

        // when
        advanceUntilIdle()

        // then
        assertEquals(myPageDto.mapper(), viewModel.myPage.value)
    }

    @Test
    fun `logout 성공 시 MoveLoginScreen 이벤트 발행`() = runTest(UnconfinedTestDispatcher()) {
        // given
        `MyViewModel 생성 시 초기 데이터 설정`()

        coEvery { removeTokenUseCase(Unit, any()) } returns Unit

        // when
        viewModel.logout()

        // then
        assertEquals(MyEvent.MoveLoginScreen, viewModel.event.first())
    }
}
