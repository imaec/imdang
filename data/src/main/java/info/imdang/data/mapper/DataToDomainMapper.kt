package info.imdang.data.mapper

interface DataToDomainMapper<T> {
    fun mapper(): T
}

internal fun <T> List<DataToDomainMapper<T>>.mapper(): List<T> = map { it.mapper() }
