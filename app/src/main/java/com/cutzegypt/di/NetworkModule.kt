package com.cutzegypt.di

import com.cutzegypt.BuildConfig
import com.cutzegypt.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    fun requestInterceptor() = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder()
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")

                .build()
        )
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(requestInterceptor: Interceptor) = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .build()
    } else OkHttpClient.Builder()
        .readTimeout(3, TimeUnit.MINUTES)
        .connectTimeout(3, TimeUnit.MINUTES)
        .addInterceptor(requestInterceptor)
        .build()


    @Provides
    @Singleton
    fun provideRetorfit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

}

