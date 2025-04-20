package info.imdang.app.viewmodel

import androidx.paging.PagingData
import info.imdang.app.model.myinsight.ComplexVo
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
        val addresses = listOf(
            MyInsightAddressDto(
                siDo = "testSido",
                siGunGu = "testSiGunGu",
                eupMyeonDong = "testEupMyeonDong",
                apartmentComplexCount = 3,
                insightCount = 5
            ),
            MyInsightAddressDto(
                siDo = "testSido",
                siGunGu = "testSiGunGu",
                eupMyeonDong = "testEupMyeonDong",
                apartmentComplexCount = 4,
                insightCount = 6
            ),
            MyInsightAddressDto(
                siDo = "testSido",
                siGunGu = "testSiGunGu",
                eupMyeonDong = "testEupMyeonDong",
                apartmentComplexCount = 2,
                insightCount = 2
            )
        )
        val complexes = listOf(
            ComplexDto(
                apartmentComplexName = "testComplexName",
                insightCount = 10
            ),
            ComplexDto(
                apartmentComplexName = "testComplexName",
                insightCount = 5
            ),
            ComplexDto(
                apartmentComplexName = "testComplexName",
                insightCount = 6
            )
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
                dto.mapper(isSelected = index == 0)
            },
            viewModel.addresses.value
        )
        assertEquals(complexes.map(ComplexDto::mapper), viewModel.complexes.value)
    }

    @Test
    fun `getSelectedDong 호출 시 selectedAddress가 선택된 address로 변경`() =
        runTest(UnconfinedTestDispatcher()) {
            // given
            `StorageViewModel 생성 시 초기 데이터 설정`()

            // when
            advanceUntilIdle()

            // then
            assertEquals(
                viewModel.addresses.value.first { it.isSelected }.eupMyeonDong,
                viewModel.getSelectedDong()
            )
        }

    @Test
    fun `selectInsightAddressPage 호출 시 selectedInsightAddressPage가 선택된 page로 변경`() =
        runTest(UnconfinedTestDispatcher()) {
            // given
            `StorageViewModel 생성 시 초기 데이터 설정`()
            val selectedPage = 1
            val complexes = listOf(
                ComplexDto(
                    apartmentComplexName = "testComplexName1",
                    insightCount = 10
                ),
                ComplexDto(
                    apartmentComplexName = "testComplexName2",
                    insightCount = 5
                ),
                ComplexDto(
                    apartmentComplexName = "testComplexName3",
                    insightCount = 6
                )
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

            coEvery { getComplexesByAddressUseCase(any(), any()) } returns complexes
            coEvery { getMyInsightsByAddressUseCase(any(), any()) } returns flow {
                PagingData.from(insightDtoList)
            }

            // when
            viewModel.selectInsightAddressPage(page = selectedPage)
            advanceUntilIdle()

            // then
            assertEquals(
                selectedPage,
                viewModel.selectedInsightAddressPage.value
            )
            assertEquals(
                viewModel.addresses.value[selectedPage].eupMyeonDong,
                viewModel.getSelectedDong()
            )
            assertEquals(complexes.map(ComplexDto::mapper), viewModel.complexes.value)
        }

    @Test
    fun `toggleMyInsightOnly 호출 시 isSeeOnlyMyInsight가 true로 변경`() =
        runTest(UnconfinedTestDispatcher()) {
            // given
            `StorageViewModel 생성 시 초기 데이터 설정`()
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

            coEvery { getMyInsightsByAddressUseCase(any(), any()) } returns flow {
                PagingData.from(insightDtoList)
            }

            // when
            viewModel.toggleMyInsightOnly()
            advanceUntilIdle()

            // then
            assertEquals(true, viewModel.isSeeOnlyMyInsight.value)
        }

    @Test
    fun `updateSelectedComplex 호출 시 selectedComplex가 선택된 complex로 변경`() =
        runTest(UnconfinedTestDispatcher()) {
            // given
            `StorageViewModel 생성 시 초기 데이터 설정`()
            val complexVo = ComplexVo(
                complexName = "testComplexName",
                insightCount = 10,
                isSelected = true
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

            coEvery { getMyInsightsByAddressUseCase(any(), any()) } returns flow {
                PagingData.from(insightDtoList)
            }

            // when
            viewModel.updateSelectedComplex(complexVo)
            advanceUntilIdle()

            // then
            assertEquals(complexVo, viewModel.selectedComplex.value)
        }
}
