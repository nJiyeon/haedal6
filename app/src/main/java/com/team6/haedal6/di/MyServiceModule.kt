package com.team6.haedal6.di

import com.team6.haedal6.api.MyApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object MyServiceModule {

    @Provides
    @Singleton
    @OtherRetrofit
    fun provideOtherRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .build()
    }

    @Provides
    @Singleton
    fun providedService(@OtherRetrofit retrofit: Retrofit): MyApiService =
        retrofit.create(MyApiService::class.java)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherRetrofit
