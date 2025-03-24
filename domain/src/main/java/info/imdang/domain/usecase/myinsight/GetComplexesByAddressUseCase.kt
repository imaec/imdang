package info.imdang.domain.usecase.myinsight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.myinsight.ComplexDto
import info.imdang.domain.repository.MyInsightRepository
import info.imdang.domain.repository.fake.FakeMyInsightRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class GetComplexesByAddressUseCase @Inject constructor(
    private val repository: MyInsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<AddressDto, List<ComplexDto>>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: AddressDto): List<ComplexDto> =
        repository.getComplexesByAddress(parameters)
}

class FakeGetComplexesByAddressUseCase : GetComplexesByAddressUseCase(
    repository = FakeMyInsightRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
