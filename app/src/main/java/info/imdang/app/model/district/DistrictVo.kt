package info.imdang.app.model.district

import info.imdang.domain.model.district.DistrictDto

data class DistrictVo(
    val siDo: String,
    val siGunGu: String,
    val eupMyeonDong: String?,
    val code: String,
    val isSelected: Boolean
) {

    companion object {
        fun getSamples(size: Int): List<DistrictVo> {
            val list = mutableListOf<DistrictVo>()
            repeat(size) {
                list.add(
                    DistrictVo(
                        siDo = "",
                        siGunGu = "강남구",
                        eupMyeonDong = "역삼동",
                        code = "",
                        isSelected = it == 0
                    )
                )
            }
            return list
        }
    }
}

fun DistrictDto.mapper(): DistrictVo = DistrictVo(
    siDo = siDo,
    siGunGu = siGunGu,
    eupMyeonDong = eupMyeonDong,
    code = code,
    isSelected = false
)
