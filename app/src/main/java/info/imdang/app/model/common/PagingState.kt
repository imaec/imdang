package info.imdang.app.model.common

data class PagingState(
    val isLoading: Boolean = true,
    val itemCount: Int = 0,
    val error: String? = null
)
