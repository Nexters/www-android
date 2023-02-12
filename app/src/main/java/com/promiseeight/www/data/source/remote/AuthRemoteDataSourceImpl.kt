package com.promiseeight.www.data.source.remote

import com.promiseeight.www.data.model.request.AccessTokenRequest
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
}