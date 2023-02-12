package com.promiseeight.www.data.source.remote

import com.promiseeight.www.data.model.request.AccessTokenRequest

/*
    Auth(DeviceID, AccessToken 등) 관련 RemoteDataSource 인터페이스
 */

interface AuthRemoteDataSource {
    suspend fun getAccessToken(accessTokenRequest: AccessTokenRequest) : String
}