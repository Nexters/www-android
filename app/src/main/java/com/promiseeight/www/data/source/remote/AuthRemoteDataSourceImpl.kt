package com.promiseeight.www.data.source.remote

import com.promiseeight.www.data.model.exception.getWwwException
import com.promiseeight.www.data.model.request.AccessTokenRequest
import com.promiseeight.www.data.model.request.MeetingConfirmRequest
import com.promiseeight.www.data.source.remote.api.AuthService
import javax.inject.Inject

/*
    Auth(AccessToken) 관련 RemoteDataSource 구현 클래스
 */

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun getAccessToken(accessTokenRequest: AccessTokenRequest): String {
        return authService.getAccessToken(accessTokenRequest).result
    }

    override suspend fun getVersion(): Result<String> {
        return try {
            val response =  authService.getVersion()

            if (response.code == 0) Result.success(response.result)
            else Result.failure(getWwwException(response.code))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}