package info.imdang.app.model.myinsight

import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.myinsight.MyInsightAddressDto

data class MyInsightAddressVo(
    val siDo: String,
    val siGunGu: String,
    val eupMyeonDong: String,
    val aptComplexCount: Int,
    val insightCount: Int,
    val isSelected: Boolean
) {
    fun toSiGuDong() = "$siDo $siGunGu $eupMyeonDong"

    fun toAddressDto(): AddressDto = AddressDto(
        siDo = siDo,
        siGunGu = siGunGu,
        eupMyeonDong = eupMyeonDong,
        roadName = null,
        buildingNumber = null,
        detail = null,
        latitude = null,
        longitude = null
    )

    companion object {
        fun getSamples(size: Int): List<MyInsightAddressVo> {
            val addresses = mutableListOf<MyInsightAddressVo>()
            repeat(size) {
                addresses.add(
                    MyInsightAddressVo(
                        siDo = "서울특별시",
                        siGunGu = "강남구",
                        eupMyeonDong = "역삼동",
                        aptComplexCount = 4,
                        insightCount = 3,
                        isSelected = it == 0
                    )
                )
            }
            return addresses
        }
    }
}

fun MyInsightAddressDto.mapper(
    isSelected: Boolean = false
): MyInsightAddressVo = MyInsightAddressVo(
    siDo = siDo,
    siGunGu = siGunGu,
    eupMyeonDong = eupMyeonDong,
    aptComplexCount = apartmentComplexCount,
    insightCount = insightCount,
    isSelected = isSelected
)
