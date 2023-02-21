package com.promiseeight.www.data.source.local

import kotlinx.coroutines.flow.Flow

/*
    Auth(DeviceID, AccessToken 등) 관련 LocalDataSource 인터페이스
 */

interface AuthLocalDataSource {

    fun getPreferenceFcmToken() : Flow<Result<String>>  // Local Preference에 저장된 발급받은 FcmToken을 받아옴

    suspend fun setFcmTokenStatus(fcmToken : String)  // FcmToken을 Local Preference에서 불러옴

    fun getDeviceId() : String

    fun getAccessToken() : Flow<Result<String>>

    suspend fun setAccessToken(accessToken : String)

}