package info.imdang.app.viewmodel

import info.imdang.app.ui.main.home.HomeEvent
import info.imdang.app.ui.main.home.HomeViewModel
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.complex.VisitedComplexDto
import info.imdang.domain.model.coupon.CouponDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.model.mypage.MyPageDto
import info.imdang.domain.usecase.complex.GetVisitedComplexesUseCase
import info.imdang.domain.usecase.coupon.GetCouponUseCase
import info.imdang.domain.usecase.coupon.IssueCouponUseCase
import info.imdang.domain.usecase.home.GetCloseTimeOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.GetFirstDateOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.SetCloseTimeOfHomeFreePassUseCase
import info.imdang.domain.usecase.home.SetFirstDateOfHomeFreePassUseCase
import info.imdang.domain.usecase.insight.GetInsightsByComplexUseCase
import info.imdang.domain.usecase.insight.GetInsightsByDateUseCase
import info.imdang.domain.usecase.insight.GetInsightsUseCase
import info.imdang.domain.usecase.myexchange.GetRequestExchangesUseCase
import info.imdang.domain.usecase.myexchange.GetRequestedExchangesUseCase
import info.imdang.domain.usecase.mypage.GetMyPageInfoUseCase
import info.imdang.domain.usecase.notification.HasNewNotificationUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var hasNewNotificationUseCase: HasNewNotificationUseCase
    private lateinit var getCouponUseCase: GetCouponUseCase
    private lateinit var getMyPageInfoUseCase: GetMyPageInfoUseCase
    private lateinit var getFirstDateOfHomeFreePassUseCase: GetFirstDateOfHomeFreePassUseCase
    private lateinit var setFirstDateOfHomeFreePassUseCase: SetFirstDateOfHomeFreePassUseCase
    private lateinit var getCloseTimeOfHomeFreePassUseCase: GetCloseTimeOfHomeFreePassUseCase
    private lateinit var setCloseTimeOfHomeFreePassUseCase: SetCloseTimeOfHomeFreePassUseCase
    private lateinit var issueCouponUseCase: IssueCouponUseCase
    private lateinit var getVisitedComplexesUseCase: GetVisitedComplexesUseCase
    private lateinit var getInsightsByComplexUseCase: GetInsightsByComplexUseCase
    private lateinit var getInsightsByDateUseCase: GetInsightsByDateUseCase
    private lateinit var getInsightsUseCase: GetInsightsUseCase
    private lateinit var getRequestExchangesUseCase: GetRequestExchangesUseCase
    private lateinit var getRequestedExchangesUseCase: GetRequestedExchangesUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        hasNewNotificationUseCase = mockk()
        getCouponUseCase = mockk()
        getMyPageInfoUseCase = mockk()
        getFirstDateOfHomeFreePassUseCase = mockk()
        setFirstDateOfHomeFreePassUseCase = mockk()
        getCloseTimeOfHomeFreePassUseCase = mockk()
        setCloseTimeOfHomeFreePassUseCase = mockk()
        issueCouponUseCase = mockk()
        getVisitedComplexesUseCase = mockk()
        getInsightsByComplexUseCase = mockk()
        getInsightsByDateUseCase = mockk()
        getInsightsUseCase = mockk()
        getRequestExchangesUseCase = mockk()
        getRequestedExchangesUseCase = mockk()

        viewModel = HomeViewModel(
            hasNewNotificationUseCase = hasNewNotificationUseCase,
            getCouponUseCase = getCouponUseCase,
            getMyPageInfoUseCase = getMyPageInfoUseCase,
            getFirstDateOfHomeFreePassUseCase = getFirstDateOfHomeFreePassUseCase,
            setFirstDateOfHomeFreePassUseCase = setFirstDateOfHomeFreePassUseCase,
            getCloseTimeOfHomeFreePassUseCase = getCloseTimeOfHomeFreePassUseCase,
            setCloseTimeOfHomeFreePassUseCase = setCloseTimeOfHomeFreePassUseCase,
            issueCouponUseCase = issueCouponUseCase,
            getVisitedComplexesUseCase = getVisitedComplexesUseCase,
            getInsightsByComplexUseCase = getInsightsByComplexUseCase,
            getInsightsByDateUseCase = getInsightsByDateUseCase,
            getInsightsUseCase = getInsightsUseCase,
            getRequestExchangesUseCase = getRequestExchangesUseCase,
            getRequestedExchangesUseCase = getRequestedExchangesUseCase
        )
    }

    @Test
    // hasNewNotification은 true로 변경
    // visitedComplexes의 첫 번째 아이템 선택
    // insightsByComplex의 size가 0보다 크고 3보다 작음
    // newInsights의 size가 0보다 크고 5보다 작음
    // recommendInsights의 size가 0보다 큼
    fun `HomeViewModel 생성 시 초기 데이터 설정`() = runTest(UnconfinedTestDispatcher()) {
        // given
        val hasNewNotification = true
        val myPageDto = MyPageDto(
            nickname = "testNickname",
            insightCount = 0,
            requestCount = 0
        )
        val couponDto = CouponDto(
            couponCount = 3,
            memberCouponId = 1
        )
        val firstDateOfHomeFreePass = 0L
        val visitedComplexes = listOf(
            VisitedComplexDto(name = "testComplex"),
            VisitedComplexDto(name = "testComplex"),
            VisitedComplexDto(name = "testComplex")
        )
        val insightDto = InsightDto(
            insightId = "testInsightId",
            recommendedCount = 0,
            address = AddressDto(
                siDo = "testSiDo",
                siGunGu = "testSiGunGu",
                eupMyeonDong = "testEupMyeonDong",
                roadName = "testRoadName",
                buildingNumber = "testBuildingNumber",
                detail = "testDetail",
                latitude = 0.0,
                longitude = 0.0
            ),
            title = "testTitle",
            mainImage = "testMainImage",
            memberId = "testMemberId",
            memberNickname = "testMemberNickname"
        )
        val insightDtoList = buildList {
            repeat(10) {
                add(insightDto)
            }
        }
        val insights = PagingDto.empty<InsightDto>().copy(content = insightDtoList)

        coEvery { hasNewNotificationUseCase(Unit, any()) } returns hasNewNotification
        coEvery { getMyPageInfoUseCase(Unit, any()) } returns myPageDto
        coEvery { getCouponUseCase(Unit, any()) } returns couponDto
        coEvery { getFirstDateOfHomeFreePassUseCase(Unit, any()) } returns null
        coEvery { getCloseTimeOfHomeFreePassUseCase(Unit, any()) } returns firstDateOfHomeFreePass
        coEvery { getVisitedComplexesUseCase(Unit, any()) } returns visitedComplexes
        coEvery { getInsightsByComplexUseCase(any(), any()) } returns insights
        coEvery { getInsightsByDateUseCase(any(), any()) } returns insights
        coEvery { getInsightsUseCase(any(), any()) } returns insights

        // when
        delay(100)
        advanceUntilIdle()

        // then
        assertEquals(true, viewModel.hasNewNotification.value)
        assertEquals(0, viewModel.visitedComplexes.value.indexOfFirst { it.isSelected })
        assert(viewModel.insightsByComplex.value.size in 0..3)
        assert(viewModel.newInsights.value.size in 0..5)
        assert(viewModel.recommendInsights.value.isNotEmpty())
    }
}
