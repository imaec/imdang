package info.imdang.app.viewmodel

import androidx.paging.PagingData
import info.imdang.app.model.myinsight.MyInsightAddressVo
import info.imdang.app.model.myinsight.mapper
import info.imdang.app.ui.main.storage.StorageViewModel
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.model.myinsight.ComplexDto
import info.imdang.domain.model.myinsight.MyInsightAddressDto
import info.imdang.domain.usecase.myinsight.GetAddressesUseCase
import info.imdang.domain.usecase.myinsight.GetComplexesByAddressUseCase
import info.imdang.domain.usecase.myinsight.GetMyInsightsByAddressUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class StorageViewModelTest {

    private lateinit var getAddressesUseCase: GetAddressesUseCase
    private lateinit var getComplexesByAddressUseCase: GetComplexesByAddressUseCase
    private lateinit var getMyInsightsByAddressUseCase: GetMyInsightsByAddressUseCase
    private lateinit var viewModel: StorageViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        getAddressesUseCase = mockk()
        getComplexesByAddressUseCase = mockk()
        getMyInsightsByAddressUseCase = mockk()

        viewModel = StorageViewModel(
            getAddressesUseCase = getAddressesUseCase,
            getComplexesByAddressUseCase = getComplexesByAddressUseCase,
            getMyInsightsByAddressUseCase = getMyInsightsByAddressUseCase
        )
    }

    @Test
    fun `StorageViewModel 생성 시 초기 데이터 설정`() = runTest(UnconfinedTestDispatcher()) {
        // given
        val addresses = listOf<MyInsightAddressDto>()
        val complexes = listOf<ComplexDto>()
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

        coEvery { getAddressesUseCase(Unit, any()) } returns addresses
        coEvery { getComplexesByAddressUseCase(any(), any()) } returns complexes
        coEvery { getMyInsightsByAddressUseCase(any(), any()) } returns flow {
            PagingData.from(insightDtoList)
        }

        // when
        advanceUntilIdle()

        // then
        assertEquals(
            addresses.mapIndexed { index, dto ->
                dto.mapper(isSelected = index == 0) },
            viewModel.addresses.value
        )
        assertEquals(complexes.map(ComplexDto::mapper), viewModel.complexes.value)
    }
}
