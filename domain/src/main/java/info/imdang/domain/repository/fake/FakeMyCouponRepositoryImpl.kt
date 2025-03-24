package info.imdang.domain.repository.fake

import info.imdang.domain.model.coupon.CouponDto
import info.imdang.domain.repository.MyCouponRepository
import javax.inject.Inject

internal class FakeMyCouponRepositoryImpl @Inject constructor() : MyCouponRepository {

    override suspend fun getCoupon(): CouponDto = CouponDto(
        couponCount = 0,
        memberCouponId = 0
    )
}
