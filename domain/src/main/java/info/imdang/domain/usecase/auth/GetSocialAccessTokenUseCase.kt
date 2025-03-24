package info.imdang.domain.usecase.auth

import info.imdang.domain.repository.AuthRepository
import info.imdang.domain.repository.fake.FakeAuthRepositoryImpl
import javax.inject.Inject

open class GetSocialAccessTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(): String = repository.getSocialAccessToken()
}

class FakeGetSocialAccessTokenUseCase : GetSocialAccessTokenUseCase(
    repository = FakeAuthRepositoryImpl()
)
