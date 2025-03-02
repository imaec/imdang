package info.imdang.app.model.myinsight

import info.imdang.app.model.common.AddressVo

data class StorageAddressVo(
    val address: AddressVo,
    val complexCount: Int,
    val insightCount: Int,
    val isSelected: Boolean
)
