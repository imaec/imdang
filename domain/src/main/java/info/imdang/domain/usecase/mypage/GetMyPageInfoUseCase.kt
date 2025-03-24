package info.imdang.domain.usecase.mypage

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.mypage.MyPageDto
import info.imdang.domain.repository.MyPageRepository
import info.imdang.domain.repository.fake.FakeMyPageRepositoryImpl
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
    repository = FakeMyPageRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
