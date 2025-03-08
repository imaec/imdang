package info.imdang.domain.usecase.notification

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.notification.NotificationDto
import info.imdang.domain.repository.NotificationRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class GetNotificationsUseCase @Inject constructor(
    private val repository: NotificationRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<PagingParams, PagingDto<NotificationDto>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: PagingParams
    ): PagingDto<NotificationDto> = repository.getNotifications(
        page = parameters.page - 1,
        size = parameters.size,
        direction = parameters.direction,
        properties = parameters.properties
    )
}

class FakeGetNotificationsUseCase : GetNotificationsUseCase(
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
