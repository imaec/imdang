package info.imdang.domain.usecase.coupon

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.coupon.CouponDto
import info.imdang.domain.repository.MyCouponRepository
import info.imdang.domain.repository.fake.FakeMyCouponRepositoryImpl
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class GetCouponUseCase @Inject constructor(
    private val repository: MyCouponRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, CouponDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit): CouponDto = repository.getCoupon()
}

class FakeGetCouponUseCase : GetCouponUseCase(
    repository = FakeMyCouponRepositoryImpl(),
    dispatcher = Dispatchers.IO
)
