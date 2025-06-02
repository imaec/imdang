package info.imdang.app.viewmodel

import androidx.lifecycle.SavedStateHandle
import info.imdang.app.model.insight.mapper
import info.imdang.app.ui.insight.write.WriteInsightViewModel
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.insight.ApartmentComplexDto
import info.imdang.domain.model.insight.InsightDetailDto
import info.imdang.domain.usecase.insight.GetInsightDetailUseCase
import info.imdang.domain.usecase.insight.UpdateInsightUseCase
import info.imdang.domain.usecase.insight.WriteInsightUseCase
import io.mockk.coEvery
import io.mockk.mockk
import java.io.File
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WriteInsightViewModelTest {

    private lateinit var getInsightDetailUseCase: GetInsightDetailUseCase
    private lateinit var writeInsightUseCase: WriteInsightUseCase
    private lateinit var updateInsightUseCase: UpdateInsightUseCase
    private lateinit var viewModel: WriteInsightViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        getInsightDetailUseCase = mockk()
        writeInsightUseCase = mockk()
        updateInsightUseCase = mockk()

        viewModel = WriteInsightViewModel(
            savedStateHandle = SavedStateHandle().apply {
                set("insightId", "testInsightId")
            },
            getInsightDetailUseCase = getInsightDetailUseCase,
            writeInsightUseCase = writeInsightUseCase,
            updateInsightUseCase = updateInsightUseCase
        )
    }

    private fun getInsightDetailDto() = InsightDetailDto(
        memberId = "testMemberId",
        memberNickname = "testMemberNickname",
        insightId = "testInsightId",
        snapshotId = 1L,
        mainImage = "testMainImage",
        title = "testTitle",
        address = AddressDto(
            siDo = "testSido",
            siGunGu = "testSigungu",
            eupMyeonDong = "testEupmyeondong",
            roadName = "testRoadname",
            buildingNumber = "testBuildingnumber",
            detail = "testDetail",
            latitude = 1.0,
            longitude = 2.0
        ),
        apartmentComplex = ApartmentComplexDto(name = "testComplexName"),
        visitAt = "2025-05-12",
        visitTimes = listOf("testVisitTime"),
        visitMethods = listOf("testVisitMethod"),
        access = "testAccess",
        summary = "testSummary",
        infra = null,
        complexEnvironment = null,
        complexFacility = null,
        favorableNews = null,
        recommended = true,
        accused = false,
        recommendedCount = 1,
        accusedCount = null,
        viewCount = 1,
        score = 1,
        createdAt = "testCreatedAt",
        createdByMe = true,
        exchangeRequestStatus = null,
        exchangeRequestCreatedByMe = null,
        exchangeRequestId = null
    )

    @Test
    fun `WriteInsightViewModel 생성 시 insightId가 null이 아니면 인사이트 상세 조회`() =
        runTest(UnconfinedTestDispatcher()) {
            // given
            val insightId = "testInsightId"
            val insight = getInsightDetailDto()

            coEvery { getInsightDetailUseCase(insightId, any()) } returns insight

            // when
            delay(100)

            // then
            assertEquals(insightId, viewModel.insightId)
            assertEquals(insight.mapper().mainImage, viewModel.coverImageUrl.value)
            assertEquals(insight.mapper().title, viewModel.title.value)
            assertEquals(insight.mapper().address.toJibunAddress(), viewModel.address.value)
            assertEquals(insight.mapper().complexName, viewModel.complexName.value)
            assertEquals(insight.mapper().visitAt, viewModel.visitDate.value)
        }

    @Test
    fun `updateProgress 호출 시 progress 값이 00%로 변경`() = runTest(UnconfinedTestDispatcher()) {
        // given
        `WriteInsightViewModel 생성 시 insightId가 null이 아니면 인사이트 상세 조회`()
        val progress = "00%"

        // when
        viewModel.updateProgress()

        // then
        assertEquals(progress, viewModel.progress.value)
    }

    @Test
    fun `updateSelectedPage 호출 시 selectedPage 값이 변경`() = runTest(UnconfinedTestDispatcher()) {
        // given
        `WriteInsightViewModel 생성 시 insightId가 null이 아니면 인사이트 상세 조회`()
        val selectedPage = 1

        // when
        viewModel.updateSelectedPage(selectedPage)

        // then
        assertEquals(selectedPage, viewModel.selectedPage.value)
    }

    @Test
    fun `updateCoverImageFile 호출 시 coverImageFile 값이 변경`() =
        runTest(UnconfinedTestDispatcher()) {
            // given
            `WriteInsightViewModel 생성 시 insightId가 null이 아니면 인사이트 상세 조회`()
            val coverImageFile = File("testCoverImageFile")

            // when
            viewModel.updateCoverImageFile(coverImageFile)

            // then
            assertEquals(coverImageFile, viewModel.coverImageFile.value)
        }

    @Test
    fun `updateTitle 호출 시 title 값이 변경`() = runTest(UnconfinedTestDispatcher()) {
        // given
        `WriteInsightViewModel 생성 시 insightId가 null이 아니면 인사이트 상세 조회`()
        val title = "testTitle"

        // when
        viewModel.updateTitle(title)

        // then
        assertEquals(title, viewModel.title.value)
    }

    @Test
    fun `updateTitleFocused 호출 시 isTitleFocused 값이 변경`() = runTest(UnconfinedTestDispatcher()) {
        // given
        `WriteInsightViewModel 생성 시 insightId가 null이 아니면 인사이트 상세 조회`()
        val isTitleFocused = true

        // when
        viewModel.updateTitleFocused(isTitleFocused)

        // then
        assertEquals(isTitleFocused, viewModel.isTitleFocused.value)
    }

    @Test
    fun `updateAddress 호출 시 address 값이 변경`() = runTest(UnconfinedTestDispatcher()) {
        // given
        `WriteInsightViewModel 생성 시 insightId가 null이 아니면 인사이트 상세 조회`()
        val address = "testAddress"

        // when
        viewModel.updateAddress(address)

        // then
        assertEquals(address, viewModel.address.value)
    }

    @Test
    fun `updateComplexName 호출 시 complexName 값이 변경`() = runTest(UnconfinedTestDispatcher()) {
        // given
        `WriteInsightViewModel 생성 시 insightId가 null이 아니면 인사이트 상세 조회`()
        val complexName = "testComplexName"

        // when
        viewModel.updateComplexName(complexName)

        // then
        assertEquals(complexName, viewModel.complexName.value)
    }

    @Test
    fun `updateVisitDate 호출 시 latitude 값이 변경`() = runTest(UnconfinedTestDispatcher()) {
        // given
        `WriteInsightViewModel 생성 시 insightId가 null이 아니면 인사이트 상세 조회`()
        val visitDate = "2025.10.10"

        // when
        viewModel.updateVisitDate(visitDate)

        // then
        assertEquals(visitDate, viewModel.visitDate.value)
    }

    @Test
    fun `updateVisitDateFocused 호출 시 isVisitDateFocused 값이 변경`() =
        runTest(UnconfinedTestDispatcher()) {
            // given
            `WriteInsightViewModel 생성 시 insightId가 null이 아니면 인사이트 상세 조회`()
            val isVisitDateFocused = true

            // when
            viewModel.updateVisitDateFocused(isVisitDateFocused)

            // then
            assertEquals(isVisitDateFocused, viewModel.isVisitDateFocused.value)
        }

    @Test
    fun `updateSummary 호출 시 summary 값이 변경`() = runTest(UnconfinedTestDispatcher()) {
        // given
        `WriteInsightViewModel 생성 시 insightId가 null이 아니면 인사이트 상세 조회`()
        val summary = "testSummary"

        // when
        viewModel.updateSummary(summary)

        // then
        assertEquals(summary, viewModel.summary.value)
    }

    @Test
    fun `updateInfraReview 호출 시 infraReview 값이 변경`() = runTest(UnconfinedTestDispatcher()) {
        // given
        `WriteInsightViewModel 생성 시 insightId가 null이 아니면 인사이트 상세 조회`()
        val infraReview = "testInfraReview"

        // when
        viewModel.updateInfraReview(infraReview)

        // then
        assertEquals(infraReview, viewModel.infraReview.value)
    }

    @Test
    fun `updateComplexEnvironmentReview 호출 시 complexEnvironmentReview 값이 변경`() =
        runTest(UnconfinedTestDispatcher()) {
            // given
            `WriteInsightViewModel 생성 시 insightId가 null이 아니면 인사이트 상세 조회`()
            val complexEnvironmentReview = "testComplexEnvironmentReview"

            // when
            viewModel.updateComplexEnvironmentReview(complexEnvironmentReview)

            // then
            assertEquals(complexEnvironmentReview, viewModel.complexEnvironmentReview.value)
        }

    @Test
    fun `updateComplexFacilityReview 호출 시 complexFacilityReview 값이 변경`() =
        runTest(UnconfinedTestDispatcher()) {
            // given
            `WriteInsightViewModel 생성 시 insightId가 null이 아니면 인사이트 상세 조회`()
            val complexFacilityReview = "testComplexFacilityReview"

            // when
            viewModel.updateComplexFacilityReview(complexFacilityReview)

            // then
            assertEquals(complexFacilityReview, viewModel.complexFacilityReview.value)
        }
}
