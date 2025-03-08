package info.imdang.app.ui.notification

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.model.notification.NotificationCategory
import info.imdang.app.model.notification.NotificationItem
import info.imdang.app.model.notification.mapper
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.notification.NotificationDto
import info.imdang.domain.usecase.notification.GetNotificationsUseCase
import info.imdang.app.model.notification.NotificationListType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<NotificationEvent>()
    val event = _event.asSharedFlow()

    private val _selectedNotificationListType = MutableStateFlow(NotificationListType.ALL)
    val selectedNotificationListType = _selectedNotificationListType.asStateFlow()

    protected val _notificationItems = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notificationItems = _notificationItems.asStateFlow()

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        viewModelScope.launch {
            val notifications = getNotificationsUseCase(
                PagingParams(
                    size = 20,
                    properties = listOf("createdAt")
                )
            )
                ?.content
                ?.map(NotificationDto::mapper)
                ?.filter {
                    when (selectedNotificationListType.value) {
                        NotificationListType.ALL -> true
                        NotificationListType.REQUEST_HISTORY -> {
                            it.category != NotificationCategory.REQUESTED
                        }
                        NotificationListType.REQUESTED_HISTORY -> {
                            it.category == NotificationCategory.REQUESTED
                        }
                    }
                } ?: emptyList()
            val newNotifications = notifications.filter { it.isNewNotification }
            val lastNotifications = notifications.filter { !it.isNewNotification }

            _notificationItems.value = mutableListOf<NotificationItem>().apply {
                if (notifications.isEmpty()) {
                    add(NotificationItem.Empty(text = "최근 1년간 도착한 알림이 없어요"))
                    return@apply
                }
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

    fun selectNotificationType(notificationListType: NotificationListType) {
        _selectedNotificationListType.value = notificationListType
        fetchNotifications()
    }
}
