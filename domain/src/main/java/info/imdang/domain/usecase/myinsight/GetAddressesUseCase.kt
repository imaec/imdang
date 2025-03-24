package info.imdang.domain.usecase.myinsight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.myinsight.MyInsightAddressDto
import info.imdang.domain.repository.MyInsightRepository
import info.imdang.domain.repository.fake.FakeMyInsightRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class GetAddressesUseCase @Inject constructor(
    private val repository: MyInsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, List<MyInsightAddressDto>>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit): List<MyInsightAddressDto> =
        repository.getAddresses()
}

class FakeGetAddressesUseCase : GetAddressesUseCase(
    repository = FakeMyInsightRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
