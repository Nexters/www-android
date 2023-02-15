package com.promiseeight.www.di

import com.promiseeight.www.data.source.local.AuthLocalDataSource
import com.promiseeight.www.data.source.local.AuthLocalDataSourceImpl
import com.promiseeight.www.data.source.remote.AuthRemoteDataSource
import com.promiseeight.www.data.source.remote.AuthRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindAuthLocalDataSource(
        authLocalDataSource : AuthLocalDataSourceImpl
    ) : AuthLocalDataSource

    @Binds
    abstract fun bindAuthRemoteDataSource(
        authRemoteDataSource : AuthRemoteDataSourceImpl
    ) : AuthRemoteDataSource
}