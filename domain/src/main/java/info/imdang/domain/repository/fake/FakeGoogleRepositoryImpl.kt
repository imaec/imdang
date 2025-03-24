package info.imdang.domain.repository.fake

import info.imdang.domain.model.google.GoogleAccessTokenDto
import info.imdang.domain.repository.GoogleRepository
import javax.inject.Inject

internal class FakeGoogleRepositoryImpl @Inject constructor() : GoogleRepository {

    override suspend fun getAccessToken(code: String): GoogleAccessTokenDto = GoogleAccessTokenDto(
        accessToken = "",
        expiresIn = 0,
        scope = "",
        tokenType = true,
        idToken = ""
    )
}
