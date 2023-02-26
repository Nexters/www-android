package com.promiseeight.www.domain.model

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.data.model.response.UserResponse

data class User(
    val characterType : String,
    val joinedUserName : String
)