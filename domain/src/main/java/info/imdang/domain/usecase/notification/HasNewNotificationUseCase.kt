package info.imdang.domain.usecase.notification

import info.imdang.domain.IoDispatcher
import info.imdang.domain.repository.NotificationRepository
import info.imdang.domain.repository.fake.FakeNotificationRepositoryImpl
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
    repository = FakeNotificationRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
