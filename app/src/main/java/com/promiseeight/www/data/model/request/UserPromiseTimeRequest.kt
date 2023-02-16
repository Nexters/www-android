package com.promiseeight.www.data.model.request

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.domain.model.PromiseInfo

data class UserPromiseTimeRequest(
    @SerializedName("promiseDate") val promiseDate : String,
    @SerializedName("promiseTime") val promiseTime : String
)

fun PromiseInfo.toUserPromiseTimeRequest() = UserPromiseTimeRequest(
    promiseDate = date,
    promiseTime = promiseTime.name
)
