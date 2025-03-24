package info.imdang.domain.repository.fake

import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.notification.NotificationDto
import info.imdang.domain.repository.NotificationRepository
import javax.inject.Inject

internal class FakeNotificationRepositoryImpl @Inject constructor() : NotificationRepository {

    override suspend fun hasNewNotification(): Boolean = true

    override suspend fun getNotifications(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<NotificationDto> = PagingDto.empty()
}
