package info.imdang.domain.usecase.notification

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.notification.NotificationDto
import info.imdang.domain.repository.NotificationRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class HasNewNotificationUseCase @Inject constructor(
    private val repository: NotificationRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, Boolean>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit): Boolean =
        repository.hasNewNotification()
}

class FakeHasNewNotificationUseCase : HasNewNotificationUseCase(
    repository = object : NotificationRepository {
        override suspend fun hasNewNotification(): Boolean {
            TODO("Not yet implemented")
        }

        override suspend fun getNotifications(
            page: Int?,
            size: Int?,
            direction: String?,
            properties: List<String>?
        ): PagingDto<NotificationDto> {
            TODO("Not yet implemented")
        }
    },
    dispatcher = Dispatchers.IO
)
