package info.imdang.data.model.response.insight

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.complex.VisitedComplexDto

data class VisitedComplexResponse(
    val name: String
) : DataToDomainMapper<VisitedComplexDto> {
    override fun mapper(): VisitedComplexDto = VisitedComplexDto(
        name = name
    )
}
