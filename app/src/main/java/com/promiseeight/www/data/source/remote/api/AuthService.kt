package com.promiseeight.www.data.source.remote.api

import com.promiseeight.www.data.model.request.AccessTokenRequest
import com.promiseeight.www.data.model.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

/*
    API 정의 인터페이스
 */

interface AuthService {
    @POST("/users/join")
    suspend fun getAccessToken(
        @Body loginRequest : AccessTokenRequest
    ) : BaseResponse<String>
}