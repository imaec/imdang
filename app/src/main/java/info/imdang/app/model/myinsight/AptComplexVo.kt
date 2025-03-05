package info.imdang.app.model.myinsight

import info.imdang.domain.model.myinsight.AptComplexDto

data class AptComplexVo(
    val aptComplexName: String,
    val insightCount: Int,
    val isSelected: Boolean
) {
    companion object {
        fun getSamples(size: Int): List<AptComplexVo> {
            val complexes = mutableListOf<AptComplexVo>()
            repeat(size) {
                complexes.add(
                    AptComplexVo(
                        aptComplexName = "신논현 더 센트럴 푸르지오",
                        insightCount = 15,
                        isSelected = it == 0
                    )
                )
            }
            return complexes
        }
    }
}

fun AptComplexDto.mapper(isSelected: Boolean = false): AptComplexVo = AptComplexVo(
    aptComplexName = apartmentComplexName,
    insightCount = insightCount ?: 0,
    isSelected = isSelected
)
