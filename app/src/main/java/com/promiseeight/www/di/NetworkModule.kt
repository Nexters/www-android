package com.promiseeight.www.di

import com.promiseeight.www.BuildConfig
import com.promiseeight.www.data.source.local.AuthLocalDataSource
import com.promiseeight.www.data.source.remote.api.TokenInterceptor
import com.promiseeight.www.data.source.remote.api.AuthService
import com.promiseeight.www.data.source.remote.api.MeetingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MeetingRetrofit


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TokenInterceptorOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class InterceptorOkHttpClient

    @Provides
    @Singleton
    fun provideAuthService(
        @AuthRetrofit retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }


    @Provides
    @Singleton
    fun provideMeetingService(
        @MeetingRetrofit retrofit: Retrofit
    ): MeetingService {
        return retrofit.create(MeetingService::class.java)
    }

    @AuthRetrofit
    @Provides
    @Singleton
    fun provideAuthRetrofit(
        @InterceptorOkHttpClient okHttpClient: OkHttpClient,
        converter: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.baseUrl)
            .addConverterFactory(converter)
            .build()
    }

    @MeetingRetrofit
    @Provides
    @Singleton
    fun provideMeetingRetrofit(
        @TokenInterceptorOkHttpClient okHttpClient: OkHttpClient,
        converter: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.baseUrl)
            .addConverterFactory(converter)
            .build()
    }

    @Provides
    @Singleton
    fun provideConvertFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @InterceptorOkHttpClient
    @Provides
    @Singleton
    fun provideAuthHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            //.connectTimeout(3, TimeUnit.SECONDS)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()
    }

    @TokenInterceptorOkHttpClient
    @Provides
    @Singleton
    fun provideHttpClient(
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            //.connectTimeout(3, TimeUnit.SECONDS)
            .addNetworkInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(
        authLocalDataSource: AuthLocalDataSource
    ): TokenInterceptor {
        return TokenInterceptor(authLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }
}