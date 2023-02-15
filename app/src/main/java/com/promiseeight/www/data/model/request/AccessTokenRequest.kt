package com.promiseeight.www.data.model.request

data class AccessTokenRequest(
    val deviceId : String,
    val fcmToken : String,
    val userName : String = ""
)