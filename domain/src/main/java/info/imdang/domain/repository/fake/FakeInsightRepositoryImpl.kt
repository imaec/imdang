package info.imdang.domain.repository.fake

import androidx.paging.PagingData
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.complex.VisitedComplexDto
import info.imdang.domain.model.insight.ApartmentComplexDto
import info.imdang.domain.model.insight.ComplexEnvironmentDto
import info.imdang.domain.model.insight.ComplexFacilityDto
import info.imdang.domain.model.insight.FavorableNewsDto
import info.imdang.domain.model.insight.InfraDto
import info.imdang.domain.model.insight.InsightDetailDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.model.insight.request.WriteInsightDto
import info.imdang.domain.repository.InsightRepository
import java.io.File
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class FakeInsightRepositoryImpl @Inject constructor() : InsightRepository {

    override suspend fun writeInsight(
        writeInsightDto: WriteInsightDto,
        mainImage: File
    ): InsightIdDto = InsightIdDto(insightId = "insightId")

    override suspend fun updateInsight(
        writeInsightDto: WriteInsightDto,
        mainImage: File?
    ): InsightIdDto = InsightIdDto(insightId = "insightId")

    override suspend fun getInsights(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<InsightDto> = PagingDto.empty()

    override suspend fun getInsightsWithPaging(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = flow {
        PagingData.empty<InsightDto>()
    }

    override suspend fun getInsightsByComplex(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        complexName: String
    ): PagingDto<InsightDto> = PagingDto.empty()

    override suspend fun getInsightsByComplexWithPaging(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        complexName: String,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = flow {
        PagingData.empty<InsightDto>()
    }

    override suspend fun getInsightsByAddress(
        siGunGu: String,
        eupMyeonDong: String,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = flow {
        PagingData.empty<InsightDto>()
    }

    override suspend fun getInsightsByDate(
        date: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): PagingDto<InsightDto> = PagingDto.empty()

    override suspend fun getInsightsByDateWithPaging(
        date: String?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = flow {
        PagingData.empty<InsightDto>()
    }

    override suspend fun getInsightDetail(insightId: String): InsightDetailDto = InsightDetailDto(
        insightId = "insightId",
        memberNickname = "",
        memberId = "",
        snapshotId = null,
        mainImage = "",
        title = "",
        address = AddressDto(
            siDo = "",
            siGunGu = "",
            eupMyeonDong = "",
            roadName = "",
            buildingNumber = "",
            detail = "",
            latitude = 0.0,
            longitude = 0.0
        ),
        apartmentComplex = ApartmentComplexDto(
            name = ""
        ),
        visitAt = "",
        visitTimes = emptyList(),
        visitMethods = emptyList(),
        access = "",
        summary = "",
        infra = InfraDto(
            transportations = emptyList(),
            schoolDistricts = emptyList(),
            amenities = emptyList(),
            facilities = emptyList(),
            surroundings = emptyList(),
            landmarks = emptyList(),
            unpleasantFacilities = emptyList(),
            text = ""
        ),
        complexEnvironment = ComplexEnvironmentDto(
            buildingCondition = "",
            security = "",
            childrenFacility = "",
            seniorFacility = "",
            text = ""
        ),
        complexFacility = ComplexFacilityDto(
            familyFacilities = emptyList(),
            multipurposeFacilities = emptyList(),
            leisureFacilities = emptyList(),
            surroundings = emptyList(),
            text = ""
        ),
        favorableNews = FavorableNewsDto(
            transportations = emptyList(),
            developments = emptyList(),
            educations = emptyList(),
            environments = emptyList(),
            cultures = emptyList(),
            industries = emptyList(),
            policies = emptyList(),
            text = ""
        ),
        recommended = false,
        accused = false,
        recommendedCount = 0,
        accusedCount = 0,
        viewCount = 0,
        score = 0,
        createdAt = "",
        createdByMe = false,
        exchangeRequestStatus = "",
        exchangeRequestCreatedByMe = false,
        exchangeRequestId = ""
    )

    override suspend fun recommendInsight(
        insightId: String,
        memberId: String
    ): InsightIdDto = InsightIdDto(insightId = "insightId")

    override suspend fun reportInsight(
        insightId: String,
        memberId: String
    ): InsightIdDto = InsightIdDto(insightId = "insightId")

    override suspend fun getVisitedComplexes(): List<VisitedComplexDto> = emptyList()
}
