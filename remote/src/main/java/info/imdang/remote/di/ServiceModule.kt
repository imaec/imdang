package info.imdang.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.imdang.remote.service.ComplexService
import info.imdang.remote.service.AuthService
import info.imdang.remote.service.CouponService
import info.imdang.remote.service.DistrictService
import info.imdang.remote.service.ExchangeService
import info.imdang.remote.service.GoogleService
import info.imdang.remote.service.InsightService
import info.imdang.remote.service.MyCouponService
import info.imdang.remote.service.MyExchangeService
import info.imdang.remote.service.MyInsightService
import info.imdang.remote.service.MyPageService
import info.imdang.remote.service.NotificationService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Provides
    @Singleton
    fun bindGoogleService(
        @Named("google") retrofit: Retrofit
    ): GoogleService = retrofit.create()

    @Provides
    @Singleton
    fun bindAuthService(
        @Named("imdang") retrofit: Retrofit
    ): AuthService = retrofit.create()

    @Provides
    @Singleton
    fun bindInsightService(
        @Named("imdang") retrofit: Retrofit
    ): InsightService = retrofit.create()

    @Provides
    @Singleton
    fun bindComplexService(
        @Named("imdang") retrofit: Retrofit
    ): ComplexService = retrofit.create()

    @Provides
    @Singleton
    fun bindMyInsightService(
        @Named("imdang") retrofit: Retrofit
    ): MyInsightService = retrofit.create()

    @Provides
    @Singleton
    fun bindCouponService(
        @Named("imdang") retrofit: Retrofit
    ): CouponService = retrofit.create()

    @Provides
    @Singleton
    fun bindMyCouponService(
        @Named("imdang") retrofit: Retrofit
    ): MyCouponService = retrofit.create()

    @Provides
    @Singleton
    fun bindExchangeService(
        @Named("imdang") retrofit: Retrofit
    ): ExchangeService = retrofit.create()

    @Provides
    @Singleton
    fun bindMyExchangeService(
        @Named("imdang") retrofit: Retrofit
    ): MyExchangeService = retrofit.create()

    @Provides
    @Singleton
    fun bindDistrictService(
        @Named("imdang") retrofit: Retrofit
    ): DistrictService = retrofit.create()

    @Provides
    @Singleton
    fun bindMyPageService(
        @Named("imdang") retrofit: Retrofit
    ): MyPageService = retrofit.create()

    @Provides
    @Singleton
    fun bindNotificationService(
        @Named("imdang") retrofit: Retrofit
    ): NotificationService = retrofit.create()
}
