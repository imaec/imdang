package info.imdang.domain.usecase.auth

import info.imdang.domain.IoDispatcher
import info.imdang.domain.repository.AuthRepository
import info.imdang.domain.repository.fake.FakeAuthRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class JoinUseCase @Inject constructor(
    private val repository: AuthRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<JoinParams, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: JoinParams) = repository.join(
        nickname = parameters.nickname,
        birthDate = parameters.birthDate,
        gender = parameters.gender,
        deviceToken = parameters.deviceToken
    )
}

data class JoinParams(
    val nickname: String,
    val birthDate: String,
    val gender: String?,
    val deviceToken: String
)

class FakeJoinUseCase : JoinUseCase(
    repository = FakeAuthRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
