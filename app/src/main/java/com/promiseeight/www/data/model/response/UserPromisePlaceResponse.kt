package com.promiseeight.www.data.model.response

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.domain.model.UserPromisePlace

data class UserPromisePlaceResponse(
    @SerializedName("promisePlace")
    val promisePlace : String,
    @SerializedName("userName")
    val userName : String,
    @SerializedName("placeId")
    val placeId : Long,
    @SerializedName("userCharacter")
    val userCharacter : String,
    @SerializedName("userInfoList")
    val userInfoList : List<UserResponse>
)

fun UserPromisePlaceResponse.toUserPromisePlace() = UserPromisePlace(
    promisePlace = promisePlace,
    userName = userName,
    placeId = placeId,
    userCharacter = userCharacter,
    userInfoList = userInfoList.map {
        it.toUser()
    }
)