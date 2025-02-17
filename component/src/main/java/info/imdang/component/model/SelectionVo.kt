package info.imdang.component.model

data class SelectionVo(
    val name: String,
    val isSelected: Boolean = false
) {
    companion object {
        fun getSamples(): List<SelectionVo> = listOf(
            SelectionVo(name = "전체", isSelected = true),
            SelectionVo(name = "선택1", isSelected = false),
            SelectionVo(name = "선택2", isSelected = false),
            SelectionVo(name = "선택3", isSelected = false),
            SelectionVo(name = "선택4", isSelected = false)
        )
    }
}
