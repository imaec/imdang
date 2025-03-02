package info.imdang.app.model.notification

enum class NotificationCategory {
    REQUESTED,
    ACCEPTED,
    REJECTED;

    companion object {
        fun fromString(category: String): NotificationCategory = entries.first {
            it.name == category
        }
    }
}
