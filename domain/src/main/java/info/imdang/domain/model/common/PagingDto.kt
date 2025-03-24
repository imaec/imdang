package info.imdang.domain.model.common

data class PagingDto<T>(
    val totalElements: Int,
    val totalPages: Int,
    val size: Int,
    val number: Int,
    val sort: SortDto,
    val numberOfElements: Int,
    val pageable: PageableDto,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean,
    val content: List<T>
) {
    companion object {
        fun <T> empty() = PagingDto<T>(
            totalElements = 0,
            totalPages = 0,
            size = 0,
            number = 0,
            sort = SortDto(
                empty = true,
                sorted = false,
                unsorted = false
            ),
            numberOfElements = 0,
            pageable = PageableDto(
                offset = 0,
                sort = SortDto(
                    empty = true,
                    sorted = false,
                    unsorted = false
                ),
                paged = false,
                pageNumber = 0,
                pageSize = 0,
                unpaged = false
            ),
            first = true,
            last = true,
            empty = true,
            content = emptyList()
        )
    }
}

data class SortDto(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

data class PageableDto(
    val offset: Int,
    val sort: SortDto,
    val paged: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val unpaged: Boolean
)
