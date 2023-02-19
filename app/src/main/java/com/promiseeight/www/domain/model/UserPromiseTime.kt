package com.promiseeight.www.domain.model

import com.google.gson.annotations.SerializedName

data class UserPromiseTime(
    val promiseDate : String,
    val promiseDayOfWeek : String,
    val promiseTime : String,
    val userNameList : List<String>
)
