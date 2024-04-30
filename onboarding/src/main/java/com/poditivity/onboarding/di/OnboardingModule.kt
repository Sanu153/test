package com.poditivity.onboarding.di

import com.poditivity.common.network.AuthIntercepter
import com.poditivity.onboarding.network.OnboardingAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object OnboardingModule {
    @Provides
    @Singleton
    fun providesOnboardingApi(client: OkHttpClient): OnboardingAPI {
        return Retrofit.Builder()
            .baseUrl(com.poditivity.onboarding.BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(OnboardingAPI::class.java)
    }

    @Singleton
    @Provides
    fun getOkHttpClient(authIntercepter: AuthIntercepter): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authIntercepter)
            .addInterceptor(interceptor)
            .build()
    }




}