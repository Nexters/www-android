package com.promiseeight.www.data.model.response

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.domain.model.UserPromisePlace

data class UserPromisePlaceResponse(
    @SerializedName("promisePlace")
    val promisePlace : String,
    @SerializedName("userName")
    val userName : String
)

fun UserPromisePlaceResponse.toUserPromisePlace() = UserPromisePlace(
    promisePlace = promisePlace,
    userName = userName
)