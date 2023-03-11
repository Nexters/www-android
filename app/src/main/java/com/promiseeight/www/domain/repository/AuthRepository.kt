package com.promiseeight.www.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun getAccessToken(fcmToken : String) : Result<String>

    suspend fun setAccessTokenInDevice(accessToken : String) : Result<Boolean>

    fun getAccessTokenSavedInDevice() : Flow<Result<String>>

    fun isAccessTokenInDevice() : Flow<Result<Boolean>>

    fun getIsFirst() : Flow<Result<Boolean>>

    suspend fun setIsFirstFalse()

    fun getVersion() : Flow<Result<String>>
}