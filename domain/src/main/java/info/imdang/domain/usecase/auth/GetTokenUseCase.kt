package info.imdang.domain.usecase.auth

import info.imdang.domain.IoDispatcher
import info.imdang.domain.repository.AuthRepository
import info.imdang.domain.repository.fake.FakeAuthRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class GetTokenUseCase @Inject constructor(
    private val repository: AuthRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, String>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit): String = repository.getAccessToken()
}

class FakeGetTokenUseCase : GetTokenUseCase(
    repository = FakeAuthRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
