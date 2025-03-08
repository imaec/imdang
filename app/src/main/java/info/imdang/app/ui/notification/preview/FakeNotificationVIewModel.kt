package info.imdang.app.ui.notification.preview

import info.imdang.app.model.notification.NotificationItem
import info.imdang.app.model.notification.NotificationVo
import info.imdang.app.ui.notification.NotificationViewModel
import info.imdang.domain.usecase.notification.FakeGetNotificationsUseCase

class FakeNotificationVIewModel : NotificationViewModel(
    getNotificationsUseCase = FakeGetNotificationsUseCase()
) {
    init {
        val notifications = NotificationVo.getSamples()
        val newNotifications = notifications.filter { it.isNewNotification }
        val lastNotifications = notifications.filter { !it.isNewNotification }

        _notificationItems.value = mutableListOf<NotificationItem>().apply {
            add(NotificationItem.Title(title = "신규 알림"))
            if (newNotifications.isNotEmpty()) {
                addAll(
                    newNotifications.map {
                        NotificationItem.Notification(it)
                    }
                )
            } else {
                add(NotificationItem.Empty(text = "오늘 도착한 신규 알림이 없어요"))
            }
            add(NotificationItem.Title(title = "지난 알림"))
            if (lastNotifications.isNotEmpty()) {
                addAll(
                    lastNotifications.map {
                        NotificationItem.Notification(it)
                    }
                )
            } else {
                add(NotificationItem.Empty(text = "지난 알림이 없어요"))
            }
        }
    }
}
