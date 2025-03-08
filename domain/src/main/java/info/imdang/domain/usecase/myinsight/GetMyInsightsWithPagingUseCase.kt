package info.imdang.domain.usecase.myinsight

import androidx.paging.PagingData
import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.model.myinsight.ComplexDto
import info.imdang.domain.model.myinsight.MyInsightAddressDto
import info.imdang.domain.repository.MyInsightRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetMyInsightsWithPagingUseCase @Inject constructor(
    private val repository: MyInsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<PagingParams, Flow<PagingData<InsightDto>>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: PagingParams
    ): Flow<PagingData<InsightDto>> = repository.getMyInsightsWithPaging(
        page = parameters.page - 1,
        size = parameters.size,
        direction = parameters.direction,
        properties = parameters.properties,
        totalCountListener = parameters.totalCountListener
    )
}

class FakeGetMyInsightsWithPagingUseCase : GetMyInsightsWithPagingUseCase(
    repository = object : MyInsightRepository {
        override suspend fun getAddresses(): List<MyInsightAddressDto> {
            TODO("Not yet implemented")
        }

        override suspend fun getComplexesByAddress(address: AddressDto): List<ComplexDto> {
            TODO("Not yet implemented")
        }

        override suspend fun getMyInsightsByAddress(
            address: AddressDto,
            complexName: String?,
            onlyMine: Boolean?,
            page: Int?,
            size: Int?,
            direction: String?,
            properties: List<String>?,
            totalCountListener: ((Int) -> Unit)?
        ): Flow<PagingData<InsightDto>> {
            TODO("Not yet implemented")
        }

        override suspend fun getMyInsights(
            page: Int?,
            size: Int?,
            direction: String?,
            properties: List<String>?
        ): PagingDto<InsightDto> {
            TODO("Not yet implemented")
        }

        override suspend fun getMyInsightsWithPaging(
            page: Int?,
            size: Int?,
            direction: String?,
            properties: List<String>?,
            totalCountListener: ((Int) -> Unit)?
        ): Flow<PagingData<InsightDto>> {
            TODO("Not yet implemented")
        }
    },
    dispatcher = Dispatchers.IO
)
