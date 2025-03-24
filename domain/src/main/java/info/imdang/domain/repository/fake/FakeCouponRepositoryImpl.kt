package info.imdang.domain.repository.fake

import info.imdang.domain.repository.CouponRepository
import javax.inject.Inject

internal class FakeCouponRepositoryImpl @Inject constructor() : CouponRepository {

    override suspend fun issueCoupon() {
    }
}
