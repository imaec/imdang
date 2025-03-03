package info.imdang.domain.usecase.coupon

import info.imdang.domain.IoDispatcher
import info.imdang.domain.repository.CouponRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class IssueCouponUseCase @Inject constructor(
    private val repository: CouponRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit) = repository.issueCoupon()
}

class FakeIssueCouponUseCase : IssueCouponUseCase(
    repository = object : CouponRepository {
        override suspend fun issueCoupon() {
            TODO("Not yet implemented")
        }
    },
    dispatcher = Dispatchers.IO
)
