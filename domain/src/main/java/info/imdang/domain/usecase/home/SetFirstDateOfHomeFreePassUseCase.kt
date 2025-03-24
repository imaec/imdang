package info.imdang.domain.usecase.home

import info.imdang.domain.DefaultDispatcher
import info.imdang.domain.repository.HomeRepository
import info.imdang.domain.repository.fake.FakeHomeRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class SetFirstDateOfHomeFreePassUseCase @Inject constructor(
    private val repository: HomeRepository,
    @DefaultDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Long, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Long) =
        repository.setFirstOpenDateOfHomeFreePassPopup(parameters)
}

class FakeSetFirstDateOfHomeFreePassUseCase : SetFirstDateOfHomeFreePassUseCase(
    repository = FakeHomeRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
