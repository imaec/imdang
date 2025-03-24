package info.imdang.domain.repository.fake

import androidx.paging.PagingData
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.model.myinsight.ComplexDto
import info.imdang.domain.model.myinsight.MyInsightAddressDto
import info.imdang.domain.repository.MyInsightRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class FakeMyInsightRepositoryImpl @Inject constructor() : MyInsightRepository {

    override suspend fun getAddresses(): List<MyInsightAddressDto> = emptyList()

    override suspend fun getComplexesByAddress(
        address: AddressDto
    ): List<ComplexDto> = emptyList()

    override suspend fun getMyInsightsByAddress(
        address: AddressDto,
        complexName: String?,
        onlyMine: Boolean?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = flow {
        PagingData.empty<InsightDto>()
    }

    override suspend fun getMyInsights(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<InsightDto> = PagingDto.empty()

    override suspend fun getMyInsightsWithPaging(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = flow {
        PagingData.empty<InsightDto>()
    }
}
