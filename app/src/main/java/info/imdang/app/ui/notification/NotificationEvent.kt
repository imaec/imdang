package info.imdang.app.ui.notification

sealed class NotificationEvent {

    data object MoveInsightDetailScreen : NotificationEvent()

    data object MoveStorage : NotificationEvent()
}
