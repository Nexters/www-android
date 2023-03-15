package com.promiseeight.www.data.repository

import com.promiseeight.www.data.model.request.AccessTokenRequest
import com.promiseeight.www.data.model.response.toMeetingMainList
import com.promiseeight.www.data.source.local.AuthLocalDataSource
import com.promiseeight.www.data.source.remote.AuthRemoteDataSource
import com.promiseeight.www.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun getAccessToken(fcmToken : String) : Result<String> {
        // 서버에서 accessToken을 받아온다.
        try {
            return Result.success(
                authRemoteDataSource.getAccessToken(
                    AccessTokenRequest(
                        authLocalDataSource.getDeviceId(),
                        fcmToken = fcmToken
                    )
                )
            )
        } catch (e : Exception) {
            return Result.failure(e)
        }

    }

    override suspend fun setAccessTokenInDevice(accessToken: String): Result<Boolean> {
        try {
            authLocalDataSource.setAccessToken(accessToken)
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(e)
        }
    }

    override fun getAccessTokenSavedInDevice(): Flow<Result<String>> {
        return authLocalDataSource.getAccessToken()
    }

    override fun isAccessTokenInDevice() : Flow<Result<Boolean>> {
        try {
            return authLocalDataSource.getAccessToken().map {
                if(it.isSuccess && it.getOrThrow().isNotBlank() && it.getOrThrow() != "NO_TOKEN")
                    Result.success(true)
                else
                    Result.failure(Exception("No_TOKEN"))
            }
        } catch (e:Exception){
            return flow {
                Result.failure<Boolean>(e)
            }
        }
    }

    override fun getIsFirst(): Flow<Result<Boolean>> {
        return authLocalDataSource.getIsFirst()
    }

    override suspend fun setIsFirstFalse() {
        authLocalDataSource.setIsFirstFalse()
    }

    override fun getVersion(): Flow<Result<String>> = flow {
        authRemoteDataSource.getVersion()
            .onSuccess {
                emit(Result.success(it))
            }.onFailure {
                emit(Result.failure(it))
            }
    }
}