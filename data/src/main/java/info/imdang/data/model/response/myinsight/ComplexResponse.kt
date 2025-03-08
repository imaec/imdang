package info.imdang.data.model.response.myinsight

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.myinsight.ComplexDto

data class ComplexResponse(
    val apartmentComplexName: String,
    val insightCount: Int?
) : DataToDomainMapper<ComplexDto> {
    override fun mapper(): ComplexDto = ComplexDto(
        apartmentComplexName = apartmentComplexName,
        insightCount = insightCount
    )
}
