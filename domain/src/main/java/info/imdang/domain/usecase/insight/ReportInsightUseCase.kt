package info.imdang.domain.usecase.insight

import androidx.paging.PagingData
import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.complex.VisitedComplexDto
import info.imdang.domain.model.insight.InsightDetailDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.model.insight.InsightIdDto
import info.imdang.domain.model.insight.request.WriteInsightDto
import info.imdang.domain.repository.InsightRepository
import info.imdang.domain.usecase.UseCase
import java.io.File
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class ReportInsightUseCase @Inject constructor(
    private val repository: InsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<ReportInsightParams, InsightIdDto>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(parameters: ReportInsightParams): InsightIdDto =
        repository.reportInsight(
            insightId = parameters.insightId,
            memberId = parameters.memberId
        )
}

data class ReportInsightParams(
    val insightId: String,
    val memberId: String
)

class FakeReportInsightUseCase : ReportInsightUseCase(
    repository = object : InsightRepository {
        override suspend fun writeInsight(
            writeInsightDto: WriteInsightDto,
            mainImage: File
        ): InsightIdDto {
            TODO("Not yet implemented")
        }

        override suspend fun updateInsight(
            writeInsightDto: WriteInsightDto,
            mainImage: File?
        ): InsightIdDto {
            TODO("Not yet implemented")
        }

        override suspend fun getInsights(
            page: Int?,
            size: Int?,
            direction: String?,
            properties: List<String>?
        ): PagingDto<InsightDto> {
            TODO("Not yet implemented")
        }

        override suspend fun getInsightsWithPaging(
            page: Int?,
            size: Int?,
            direction: String?,
            properties: List<String>?,
            totalCountListener: ((Int) -> Unit)?
        ): Flow<PagingData<InsightDto>> {
            TODO("Not yet implemented")
        }

        override suspend fun getInsightsByComplex(
            page: Int?,
            size: Int?,
            direction: String?,
            properties: List<String>?,
            complexName: String
        ): PagingDto<InsightDto> {
            TODO("Not yet implemented")
        }

        override suspend fun getInsightsByComplexWithPaging(
            page: Int?,
            size: Int?,
            direction: String?,
            properties: List<String>?,
            complexName: String,
            totalCountListener: ((Int) -> Unit)?
        ): Flow<PagingData<InsightDto>> {
            TODO("Not yet implemented")
        }

        override suspend fun getInsightsByAddress(
            siGunGu: String,
            eupMyeonDong: String,
            page: Int?,
            size: Int?,
            direction: String?,
            properties: List<String>?,
            totalCountListener: ((Int) -> Unit)?
        ): Flow<PagingData<InsightDto>> {
            TODO("Not yet implemented")
        }

        override suspend fun getInsightsByDate(
            date: String?,
            page: Int?,
            size: Int?,
            direction: String?,
            properties: List<String>?,
            totalCountListener: ((Int) -> Unit)?
        ): PagingDto<InsightDto> {
            TODO("Not yet implemented")
        }

        override suspend fun getInsightsByDateWithPaging(
            date: String?,
            page: Int?,
            size: Int?,
            direction: String?,
            properties: List<String>?,
            totalCountListener: ((Int) -> Unit)?
        ): Flow<PagingData<InsightDto>> {
            TODO("Not yet implemented")
        }

        override suspend fun getInsightDetail(insightId: String): InsightDetailDto {
            TODO("Not yet implemented")
        }

        override suspend fun recommendInsight(insightId: String, memberId: String): InsightIdDto {
            TODO("Not yet implemented")
        }

        override suspend fun reportInsight(insightId: String, memberId: String): InsightIdDto {
            TODO("Not yet implemented")
        }

        override suspend fun getVisitedComplexes(): List<VisitedComplexDto> {
            TODO("Not yet implemented")
        }
    },
    dispatcher = Dispatchers.IO
)
