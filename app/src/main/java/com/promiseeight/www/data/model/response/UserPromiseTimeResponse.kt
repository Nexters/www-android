package com.promiseeight.www.data.model.response

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.domain.model.PromiseTime
import com.promiseeight.www.domain.model.UserPromiseTime

data class UserPromiseTimeResponse(
    @SerializedName("promiseDate")
    val promiseDate : String,
    @SerializedName("promiseDayOfWeek")
    val promiseDayOfWeek : String,
    @SerializedName("promiseTime")
    val promiseTime : String,
    @SerializedName("userNameList")
    val userNameList : List<String>
)

fun UserPromiseTimeResponse.toUserPromiseTime() = UserPromiseTime(
    promiseDate = promiseDate,
    promiseDayOfWeek = promiseDayOfWeek,
    promiseTime = PromiseTime.valueOf(promiseTime),
    userNameList = userNameList
)