package info.imdang.domain.usecase.home

import info.imdang.domain.DefaultDispatcher
import info.imdang.domain.repository.HomeRepository
import info.imdang.domain.repository.fake.FakeHomeRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class GetCloseTimeOfHomeFreePassUseCase @Inject constructor(
    private val repository: HomeRepository,
    @DefaultDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, Long>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit): Long =
        repository.getCloseTimeOfHomeFreePassPopup()
}

class FakeGetCloseTimeOfHomeFreePassUseCase : GetCloseTimeOfHomeFreePassUseCase(
    repository = FakeHomeRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
