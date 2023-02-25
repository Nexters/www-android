package com.promiseeight.www.data.model.request

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.domain.model.UserPromiseTime

data class UserPromiseTimeRequest(
    @SerializedName("promiseDate") val promiseDate : String,
    @SerializedName("promiseTime") val promiseTime : String
)

fun UserPromiseTime.toUserPromiseTimeRequest() = UserPromiseTimeRequest(
    promiseDate = promiseDate,
    promiseTime = promiseTime.name
)
