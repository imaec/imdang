package info.imdang.app.model.insight

enum class ExchangeType(private val id: Int) {
    REQUEST(0),
    REQUESTED(1);

    companion object {
        fun fromId(id: Int) = entries.firstOrNull { it.id == id } ?: REQUEST
    }
}
