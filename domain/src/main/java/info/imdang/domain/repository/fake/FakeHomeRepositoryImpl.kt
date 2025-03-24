package info.imdang.domain.repository.fake

import info.imdang.domain.repository.HomeRepository
import javax.inject.Inject

internal class FakeHomeRepositoryImpl @Inject constructor() : HomeRepository {

    override suspend fun setFirstOpenDateOfHomeFreePassPopup(openDate: Long) {
    }

    override suspend fun getFirstOpenDateOfHomeFreePassPopup(): Long = 0L

    override suspend fun setCloseTimeOfHomeFreePassPopup(closeTime: Long) {
    }

    override suspend fun getCloseTimeOfHomeFreePassPopup(): Long = 0L
}
