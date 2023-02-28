package com.promiseeight.www.domain.model

import com.google.gson.annotations.SerializedName

data class UserPromisePlace(
    val placeId : Long,
    val promisePlace : String,
    val userName : String,
    val userCharacter : String,
)
