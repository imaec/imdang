package info.imdang.domain.usecase.mypage

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.mypage.MyPageDto
import info.imdang.domain.repository.MyPageRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class GetMyPageInfoUseCase @Inject constructor(
    private val repository: MyPageRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, MyPageDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(
        parameters: Unit
    ): MyPageDto = repository.getMyPageInfo()
}

class FakeGetMyPageInfoUseCase : GetMyPageInfoUseCase(
    repository = object : MyPageRepository {
        override suspend fun getMyPageInfo(): MyPageDto {
            TODO("Not yet implemented")
        }

        override suspend fun withdrawalKakao(accessToken: String) {
            TODO("Not yet implemented")
        }

        override suspend fun withdrawalGoogle(accessToken: String) {
            TODO("Not yet implemented")
        }
    },
    dispatcher = Dispatchers.IO
)
