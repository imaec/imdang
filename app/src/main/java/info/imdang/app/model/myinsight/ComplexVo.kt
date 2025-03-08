package info.imdang.app.model.myinsight

import info.imdang.domain.model.myinsight.ComplexDto

data class ComplexVo(
    val complexName: String,
    val insightCount: Int,
    val isSelected: Boolean
) {
    companion object {
        fun getSamples(size: Int): List<ComplexVo> {
            val complexes = mutableListOf<ComplexVo>()
            repeat(size) {
                complexes.add(
                    ComplexVo(
                        complexName = "신논현 더 센트럴 푸르지오",
                        insightCount = 15,
                        isSelected = it == 0
                    )
                )
            }
            return complexes
        }
    }
}

fun ComplexDto.mapper(isSelected: Boolean = false): ComplexVo = ComplexVo(
    complexName = apartmentComplexName,
    insightCount = insightCount ?: 0,
    isSelected = isSelected
)
