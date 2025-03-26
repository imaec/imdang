package info.imdang.app.viewmodel

import info.imdang.app.ui.join.JoinEvent
import info.imdang.app.ui.join.JoinViewModel
import info.imdang.component.model.SelectionVo
import info.imdang.domain.usecase.auth.JoinUseCase
import info.imdang.domain.usecase.auth.RemoveTokenUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class JoinViewModelTest {

    private lateinit var joinUseCase: JoinUseCase
    private lateinit var removeTokenUseCase: RemoveTokenUseCase

    private lateinit var viewModel: JoinViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        joinUseCase = mockk()
        removeTokenUseCase = mockk()
        viewModel = JoinViewModel(joinUseCase, removeTokenUseCase)
    }

    @Test
    fun `updateAllAgree 호출 시 isAgreeTerm, isAgreePrivacy, isAgreeMarketing이 true로 변경`() = runTest {
        // given

        // when
        viewModel.updateAllAgree()
        delay(100)

        // then
        assertEquals(
            true,
            viewModel.isAgreeTerm.value &&
                viewModel.isAgreePrivacy.value &&
                viewModel.isAgreeMarketing.value
        )
    }

    @Test
    fun `updateAgreeTerm, updateAgreePrivacy 호출 시 isAgreeAndContinueButtonEnabled가 true로 변경`() = runTest {
        // given

        // when
        viewModel.updateAgreeTerm()
        viewModel.updateAgreePrivacy()
        delay(100)

        // then
        assertEquals(true, viewModel.isAgreeAndContinueButtonEnabled.value)
    }

    @Test
    fun `updateNickname 호출 시 nickname이 변경`() = runTest {
        // given
        val nickname = "testNickname"

        // when
        viewModel.updateNickname(nickname)
        delay(100)

        // then
        assertEquals(nickname, viewModel.nickname.value)
    }

    @Test
    fun `updateBirthDate 호출 시 birthDate가 변경`() = runTest {
        // given
        val birthDate = "2000.01.01"

        // when
        viewModel.updateBirthDate(birthDate)
        delay(100)

        // then
        assertEquals(birthDate, viewModel.birthDate.value)
    }

    @Test
    fun `updateGender 호출 시 gender가 변경`() = runTest {
        // given
        val gender = SelectionVo("남자")

        // when
        viewModel.updateGender(gender)
        delay(100)

        // then
        assertEquals(gender.name, viewModel.genders.value.first { it.isSelected }.name)
    }

    @Test
    fun `validateNickname 호출 시 nickname이 유효하면 nicknameErrorMessage가 빈 값으로 변경`() = runTest {
        // given
        val nickname = "nickname"

        // when
        viewModel.updateNickname(nickname)
        delay(100)

        // then
        assert(viewModel.nicknameErrorMessage.value.isBlank())
    }

    @Test
    fun `validateNickname 호출 시 nickname이 유효하지 않으면 nicknameErrorMessage가 변경`() = runTest {
        // given
        val nickname = "testNicknameOver10"

        // when
        viewModel.updateNickname(nickname)
        delay(100)

        // then
        assert(viewModel.nicknameErrorMessage.value.isNotBlank())
    }

    @Test
    fun `validateBirthDate 호출 시 nickname이 유효하면 nicknameErrorMessage가 빈 값으로 변경`() = runTest {
        // given
        val birthDate = "1991.03.19"

        // when
        viewModel.updateBirthDate(birthDate)
        delay(100)

        // then
        assert(viewModel.birthDateErrorMessage.value.isBlank())
    }

    @Test
    fun `validateBirthDate 호출 시 nickname이 유효하지 않으면 nicknameErrorMessage가 변경`() = runTest {
        // given
        val birthDate = "1991-03-19"

        // when
        viewModel.updateBirthDate(birthDate)
        delay(100)

        // then
        assert(viewModel.birthDateErrorMessage.value.isNotBlank())
    }

    @Test
    fun `join 호출 시 MoveJoinCompleteScreen 이벤트 발행`() = runTest(UnconfinedTestDispatcher()) {
        // given
        val gender = SelectionVo("남자")
        val deviceToken = "testDeviceToken"

        viewModel.updateGender(gender)
        delay(100)

        coEvery { joinUseCase(any(), any()) } returns Unit

        // when
        viewModel.join(deviceToken)

        // then
        assertEquals(JoinEvent.MoveJoinCompleteScreen, viewModel.event.first())
    }
}
