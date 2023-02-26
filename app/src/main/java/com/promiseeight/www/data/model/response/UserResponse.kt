package com.promiseeight.www.data.model.response

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.domain.model.User

data class UserResponse(
    @SerializedName("characterType") val characterType: String,
    @SerializedName("joinedUserName") val joinedUserName : String
)

fun UserResponse.toUser() = User(
    characterType = characterType,
    joinedUserName = joinedUserName
)
