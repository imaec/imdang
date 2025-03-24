package info.imdang.domain.usecase.auth

import info.imdang.domain.repository.AuthRepository
import info.imdang.domain.repository.fake.FakeAuthRepositoryImpl
import javax.inject.Inject

open class GetMemberIdUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(): String = repository.getMemberId()
}

class FakeGetMemberIdUseCase : GetMemberIdUseCase(
    repository = FakeAuthRepositoryImpl()
)
