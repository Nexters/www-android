package com.promiseeight.www.di

import com.promiseeight.www.data.repository.AuthRepositoryImpl
import com.promiseeight.www.data.source.local.AuthLocalDataSource
import com.promiseeight.www.data.source.local.AuthLocalDataSourceImpl
import com.promiseeight.www.data.source.remote.AuthRemoteDataSource
import com.promiseeight.www.data.source.remote.AuthRemoteDataSourceImpl
import com.promiseeight.www.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        authRepository : AuthRepositoryImpl
    ) : AuthRepository

}