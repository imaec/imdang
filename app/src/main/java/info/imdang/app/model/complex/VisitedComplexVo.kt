package info.imdang.app.model.complex

import info.imdang.domain.model.complex.VisitedComplexDto

data class VisitedComplexVo(
    val complexName: String,
    val isSelected: Boolean
) {
    companion object {
        fun getSamples(size: Int): List<VisitedComplexVo> {
            val complexes = mutableListOf<VisitedComplexVo>()
            repeat(size) {
                complexes.add(
                    VisitedComplexVo(
                        complexName = "신논현 더 센트럴 푸르지오",
                        isSelected = it == 0
                    )
                )
            }
            return complexes
        }
    }
}

fun VisitedComplexDto.mapper(isSelected: Boolean = false): VisitedComplexVo = VisitedComplexVo(
    complexName = name,
    isSelected = isSelected
)
